package com.store.secondlife.view.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.store.secondlife.MainActivity
import com.store.secondlife.R


class LoginFragment : Fragment(), View.OnClickListener {
    private var btnSignIn: Button ?=null
    private var btnSignUp: Button ?=null
   private var btnGoogle: Button ?=null

    lateinit var btnFacebook: Button
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private var buttonFacebookLogin: LoginButton? = null

    private lateinit var txtusu: EditText
    private lateinit var txtpass: EditText


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
        private const val TAG2 = "EmailPassword"
        private const val TAG3 = "FacebookLogin"
    }

    private lateinit var emauth: FirebaseAuth
    private lateinit var goauth: FirebaseAuth
    private lateinit var fbauth: FirebaseAuth

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
        btnSignIn?.setOnClickListener(this)
        btnSignUp = view.findViewById(R.id.btnSignUp)
        btnSignUp?.setOnClickListener(this)
        btnGoogle = view.findViewById(R.id.btnGoogle)
        btnGoogle?.setOnClickListener(this)

        txtusu = view.findViewById(R.id.txtusu)
        txtpass = view.findViewById(R.id.txtpass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        emauth = Firebase.auth
        fbauth = Firebase.auth

        //Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()

        buttonFacebookLogin?.setReadPermissions("email", "public_profile")
        buttonFacebookLogin?.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG3, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG3, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG3, "facebook:onError", error)
            }
        })
        // [END initialize_fblogin]

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        // Initialize Firebase Auth
        emauth = Firebase.auth
        goauth = Firebase.auth

        btnGoogle?.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUsergo = goauth.currentUser
        updateUI(currentUsergo)

        val currentUser = emauth.currentUser
        if (currentUser != null) {
            reload()
        }

        val currentUserfb = fbauth.currentUser
        updateUI(currentUserfb)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if(task.isSuccessful){
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
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        goauth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = goauth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    // [START auth_with_facebook]
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        fbauth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG3, "signInWithCredential:success")
                    val user = fbauth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG3, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    // [END auth_with_facebook]

    override fun onClick(p0: View?) {

        if (btnSignIn == p0) {
            val usu: String = txtusu.text.toString().trim()
            val pass: String = txtpass.text.toString().trim()

            emauth.signInWithEmailAndPassword(usu,pass)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = emauth.currentUser
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        requireActivity().startActivity(intent)
                        requireActivity().finish()
                        updateUI(user)
                        findNavController().navigate(R.id.navProfileFragment)
                    } else {
                        Log.w(TAG2, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            context, "No se puede realizar esta acción.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        } else if (btnSignUp == p0) {
            findNavController().navigate(R.id.registerFragment)
        }

       if (btnGoogle ==p0){

            signIn()
            findNavController().navigate(R.id.profileInformationDialogFragment)
        }else {
           Toast.makeText(context, "Authentication failed.",
               Toast.LENGTH_SHORT).show()
           updateUI(null)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    private fun reload() {

    }
}
