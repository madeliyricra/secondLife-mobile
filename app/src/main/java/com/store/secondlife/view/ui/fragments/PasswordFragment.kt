package com.store.secondlife.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.store.secondlife.R
import kotlinx.android.synthetic.main.fragment_password.*

class PasswordFragment : Fragment(),View.OnClickListener {

private lateinit var btnsend:Button
lateinit var btnregresar:Button
private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnsend=view.findViewById(R.id.btnsend)
        btnsend.setOnClickListener(this)
        btnregresar=view.findViewById(R.id.btnregresar)
        btnregresar.setOnClickListener(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth


//        btnsend.setOnClickListener {
//            val email: String = etCorreo.text.toString().trim { it <= ' ' }
//            if (email.isEmpty()) {
//                Toast.makeText(
//                    context,
//                    resources.getString(R.string.errorusua),
//                    Toast.LENGTH_SHORT
//                ).show()
//
//            } else {
//
//                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(
//                                context,
//                                resources.getString(R.string.send_succes),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        } else {
//
//                            Toast.makeText(
//                                context,
//                                "Error",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//            }
//        }
//
//        btnregresar.setOnClickListener {
//            findNavController().navigate(R.id.loginFragment)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onClick(p0: View?) {
        if (p0 == btnsend) {
            val emailAddress: String = etCorreo.text.toString().trim { it <= ' ' }


            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            resources.getString(R.string.send_succes),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                        Toast.makeText(
                            context,
                            "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else if (p0 == btnregresar) {
            findNavController().navigate(R.id.loginFragment)

        }
    }

}