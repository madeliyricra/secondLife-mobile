package com.store.secondlife.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.store.secondlife.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), View.OnClickListener {

    lateinit var btnlogout: Button
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnclick()
        btnlogout = view.findViewById(R.id.btnlogout)
        btnlogout.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    fun btnclick() {
        val btn = arrayOf(btn_personal_data, btn_addresses, btn_buy, btn_sales, btn_cards)
        var num = 0
        for (b in btn.indices) {
            println(btn[b].toString())
            btn[b].setOnClickListener {
                num = b
                val bundle: Bundle = Bundle()
                bundle.putInt("num", num)
                findNavController().navigate(R.id.profileInformationDialogFragment, bundle)
            }
        }
    }

    override fun onClick(p0: View?) {

        if (p0 == btnlogout) {
            val user = Firebase.auth.currentUser
            if (user != null) {
                signOut()
                Toast.makeText(
                    context, "Sesión cerrada",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.navHomeFragment)
            }else if(p0==btnlogout){
                val user = Firebase.auth.currentUser
                if (user == null) {
                    Toast.makeText(
                        context, "Debes iniciar sesión para realizar esta acción",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else { }

    }
        private fun signOut() {
            // [START auth_sign_out]
            Firebase.auth.signOut()
            // [END auth_sign_out]
        }

        private fun reload() {

        }
    }