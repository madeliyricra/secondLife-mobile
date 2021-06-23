package com.store.secondlife.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.store.secondlife.R
import com.store.secondlife.view.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnok.setOnClickListener {
            registro()
        }
        btncancel.setOnClickListener {
            onBackPressed()
        }

    }

    private fun validarRegistro(): Boolean {
        return when {
            TextUtils.isEmpty(usua.text.toString().trim { it <= ' ' }) -> {
                errorsnackbar(resources.getString(R.string.errorusua), errorMessage = true)
                false
            }
            TextUtils.isEmpty(txtUser.text.toString().trim { it <= ' ' }) -> {
                errorsnackbar(resources.getString(R.string.erroruser), errorMessage = true)
                false
            }
            TextUtils.isEmpty(txtDNI.text.toString().trim { it <= ' ' }) -> {
                errorsnackbar(resources.getString(R.string.errordni), errorMessage = true)
                false
            }
            TextUtils.isEmpty(txtFirstName.text.toString().trim { it <= ' ' }) -> {
                errorsnackbar(resources.getString(R.string.errornombre), errorMessage = true)
                false
            }
            TextUtils.isEmpty(txtLastName.text.toString().trim { it <= ' ' }) -> {
                errorsnackbar(resources.getString(R.string.errorapellido), errorMessage = true)
                false
            }
            TextUtils.isEmpty(passw.text.toString().trim { it <= ' ' }) -> {
                errorsnackbar(resources.getString(R.string.errorpass), errorMessage = true)
                false
            }
            else -> {
                //errorsnackbar("Campos llenados correctamente.", false)
                true
            }
        }
    }

    private fun registro() {
        if (validarRegistro()) {

            MostrarBarra(resources.getString(R.string.wait))

            val email: String = usua.text.toString().trim { it <= ' ' }
            val password: String = passw.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( OnCompleteListener <AuthResult> { task ->
                    Hideprogress()
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        errorsnackbar(
                             "Registrado Exitosamente. Tu usuario es ${firebaseUser.uid}",
                             false
                        )
                        FirebaseAuth.getInstance().signOut()
                        finish()
                    } else {
                        errorsnackbar(task.exception!!.message.toString(), errorMessage = true)
                    }
                })

        }
    }
}