package com.store.secondlife.view.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.store.secondlife.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp: Button
    private lateinit var usua: EditText
    private lateinit var passw: EditText
    private lateinit var forgot:TextView
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private lateinit var btnFacebook: Button
    lateinit var btnGoogle:Button

    companion object {
        private const val TAG1 = "EmailPassword"
        private const val TAG2 = "FacebookLogin"
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth

        callbackManager = CallbackManager.Factory.create()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignIn = view.findViewById(R.id.btnSignIn)
        btnSignIn.setOnClickListener(this)
        btnSignUp = view.findViewById(R.id.btnSignUp)
        btnSignUp.setOnClickListener(this)
        forgot=view.findViewById(R.id.forgot)
        forgot.setOnClickListener(this)

        usua = view.findViewById(R.id.usua)
        passw = view.findViewById(R.id.passw)

        forgot.setOnClickListener {
            findNavController().navigate(R.id.passwordFragment)
        }

        btnGoogle= view.findViewById(R.id.btnGoogle)
        btnGoogle.setOnClickListener(this)
        btnFacebook=view.findViewById(R.id.btnFacebook)
        btnFacebook.setOnClickListener(this)
        btnGoogle.setOnClickListener {
            signIn()
            findNavController().navigate(R.id.navProfileFragment)
        }
        btnFacebook.setOnClickListener {

            @Suppress("DEPRECATION")

            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            buttonFacebookLogin.registerCallback(callbackManager, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG2, "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG2, "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG2, "facebook:onError", error)
                }
            })
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)

        if (currentUser != null) {
            reload()
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG2, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG2, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG2, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    @Suppress("DEPRECATION")
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onClick(p0: View?) {

        if (btnSignIn == p0) {
            val usu: String = usua.text.toString().trim { it <= ' ' }
            val pass: String = passw.text.toString().trim { it <= ' ' }

            auth.signInWithEmailAndPassword(usu, pass)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user)

                        val bundle: Bundle = Bundle()
                        bundle.putString("usuario",usu)
                        findNavController().navigate(R.id.navProfileFragment, bundle)

                    } else {
                        Log.w(TAG1, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            context, "Error al iniciar sesión, vuelve a intentaarlo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else if (btnSignUp == p0) {
            findNavController().navigate(R.id.registerFragment)
        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {
    }
}
