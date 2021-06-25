package com.store.secondlife.view.ui.fragments

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
import com.store.secondlife.model.Usuario
import com.store.secondlife.network.FirestoreService

class RegisterFragment : Fragment(), View.OnClickListener {

    private lateinit var btnok: Button
    private lateinit var btncancel: Button

    lateinit var usua: EditText
    lateinit var txtUser: EditText
    lateinit var txtDNI: EditText
    lateinit var txtFirstName: EditText
    lateinit var txtLastName: EditText
    lateinit var passw: EditText

    private lateinit var auth: FirebaseAuth

    private var  firebase: FirestoreService = FirestoreService()

    companion object {
        private const val TAG = "EmailPassword"
    }

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
        usua = view.findViewById(R.id.usua)
        txtUser = view.findViewById(R.id.txtUser)
        txtDNI = view.findViewById(R.id.txtDNI)
        txtFirstName = view.findViewById(R.id.txtFirstName)
        txtLastName = view.findViewById(R.id.txtLastName)
        passw = view.findViewById(R.id.passw)

        btnok = view.findViewById(R.id.btnok)
        btncancel = view.findViewById(R.id.btncancel)
        btnok.setOnClickListener(this)
        btncancel.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        if (p0 == btnok) {
            val email: String = usua.text.toString().trim()
            val contra: String = passw.text.toString().trim()

            val u:Usuario= Usuario()
            u.dni=txtDNI.text.toString()       
            u.nombre=txtFirstName.text.toString()
            u.apellido=txtLastName.text.toString()
            u.usuario=txtUser.text.toString()
            u.email=usua.text.toString().trim()

            firebase.addUser(u)

            auth.createUserWithEmailAndPassword(email,contra)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        requireActivity().startActivity(intent)
                        requireActivity().finish()
                        updateUI(user)
                        findNavController().navigate(R.id.loginFragment)

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            context, "No se puede realizar esta acción",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        } else if (p0 == btncancel) {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

}