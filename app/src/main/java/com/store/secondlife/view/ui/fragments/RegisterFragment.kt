package com.store.secondlife.view.ui.fragments

import android.content.ContentValues.TAG
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

class RegisterFragment : Fragment(), View.OnClickListener {
    lateinit var btnok: Button
    lateinit var btncancel: Button

    lateinit var txtEmail: EditText
    lateinit var txtUser: EditText
    lateinit var txtDNI: EditText
    lateinit var txtFirstName: EditText
    lateinit var txtLastName: EditText
    lateinit var txtPassword: EditText
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtEmail = view.findViewById(R.id.txtEmail)
        txtUser = view.findViewById(R.id.txtUser)
        txtDNI = view.findViewById(R.id.txtDNI)
        txtFirstName = view.findViewById(R.id.txtFirstName)
        txtLastName = view.findViewById(R.id.txtLastName)
        txtPassword = view.findViewById(R.id.txtPassword)

        btnok = view.findViewById(R.id.btnok)
        btncancel = view.findViewById(R.id.btncancel)
        btnok.setOnClickListener(this)
        btncancel.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        if (p0 == btnok) {
            val email: String = txtEmail.text.toString()
            val contra: String = txtPassword.text.toString()

            auth.createUserWithEmailAndPassword(email, contra)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        Toast.makeText(
                            context, "Usuario creado exitosamente.",
                            Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        updateUI(user)
                        /*val intent:Intent=Intent(requireContext(),MainActivity::class.java)
                        requireActivity().startActivity(intent)
                        requireActivity().finish()*/
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        } else if (p0 == btncancel) {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}