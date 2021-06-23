package com.store.secondlife.auth

import android.os.Bundle
import com.store.secondlife.R
import com.store.secondlife.view.ui.activities.BaseActivity

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

//        btnSend.setOnClickListener {
//            val email: String = etCorreo.text.toString().trim { it <= ' ' }
//            if (email.isEmpty()) {
//                errorsnackbar(resources.getString(R.string.errorusua), true)
//            } else {
//                MostrarBarra(resources.getString(R.string.wait))
//                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
//                    .addOnCompleteListener { task ->
//                        Hideprogress()
//                        if (task.isSuccessful) {
//                            Toast.makeText(
//                                this@ForgotPasswordActivity,
//                                resources.getString(R.string.send_succes),
//                                Toast.LENGTH_LONG
//                            ).show()
//                            finish()
//                        } else {
//                            errorsnackbar(task.exception!!.message.toString(), true)
//                        }
//                    }
//            }
//        }

//        regresar.setOnClickListener {
//            onBackPressed()
//        }
    }
}