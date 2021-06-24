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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.store.secondlife.R


class LoginFragment : Fragment(), View.OnClickListener {
    lateinit var btnSignIn: Button
    lateinit var btnSignUp: Button
    lateinit var usua: EditText
    lateinit var passw: EditText

    lateinit var sign_in_button:Button
    private lateinit var googleSignInClient: GoogleSignInClient


    companion object {
        private const val TAG1 = "EmailPassword"
        private const val TAG2 = "GoogleActivity"
        const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//        sign_in_button.setOnClickListener(View.OnClickListener {
//            signIn()
//        })


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

        sign_in_button= view.findViewById(R.id.sign_in_button)
        sign_in_button.setOnClickListener(this)

        usua = view.findViewById(R.id.usua)
        passw = view.findViewById(R.id.passw)
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        updateUI(currentUser)
        if (currentUser != null) {
            reload()
        }
    }
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG2, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG2, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
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
                    updateUI(null)
                }
            }
    }


    @Suppress("DEPRECATION")
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        findNavController().navigate(R.id.navHomeFragment)
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


                        findNavController().navigate(R.id.navHomeFragment)
                    } else {
                        Log.w(TAG1, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            context, "No se puede realizar esta acción.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else if (btnSignUp == p0) {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        if (p0==sign_in_button){
            signIn()
            Toast.makeText(
                context, "F!",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.navProfileFragment)

        }else{

        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {
    }
}
