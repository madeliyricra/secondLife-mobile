package com.store.secondlife.view.ui.fragments

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.store.secondlife.R


class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp: Button
    private lateinit var usua: EditText
    private lateinit var passw: EditText
    private lateinit var forgot:TextView

    companion object {
        private const val TAG1 = "EmailPassword"
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth

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
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
             if (currentUser != null) {
            reload()
        }
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
