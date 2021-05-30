package com.store.secondlife.view.ui.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.store.secondlife.MainActivity
import com.store.secondlife.R

class LoginFragment : Fragment(), View.OnClickListener {
    lateinit var btnSignIn: Button
    lateinit var btnSignUp: Button

    lateinit var txtusu: EditText
    lateinit var txtpass: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth

        if (auth.currentUser != null) {

            val user = auth.currentUser
            val intent: Intent = Intent(requireContext(), MainActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
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

        txtusu = view.findViewById(R.id.txtusu)
        txtpass = view.findViewById(R.id.txtpass)
    }


    override fun onClick(p0: View?) {

        if (btnSignIn == p0) {
            val usu: String = txtusu.text.toString()
            val pass: String = txtpass.text.toString()
            auth.signInWithEmailAndPassword(usu, pass)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                        findNavController().navigate(R.id.navProfileFragment)
                    } else {
                        
                    }
                }
        } else if (btnSignUp == p0) {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    private fun updateUI(user: FirebaseUser?) {

    }
}
