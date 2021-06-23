package com.store.secondlife.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.store.secondlife.R
import com.store.secondlife.view.ui.activities.BaseActivity
import com.store.secondlife.view.ui.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity: BaseActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignUp.setOnClickListener(this)
        btnSignIn.setOnClickListener(){
            val intent = Intent(this@LoginActivity, R.id.navProfileFragment::class.java)
            startActivity(intent)
        }
        forgot.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.forgot -> {
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btnSignIn -> {
                    login()
                }

                R.id.btnSignUp -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validarLogin(): Boolean {
        return when {
            TextUtils.isEmpty(usua.text.toString().trim { it <= ' ' }) -> {
                errorsnackbar(resources.getString(R.string.errorusua), true)
                false
            }
            TextUtils.isEmpty(passw.text.toString().trim { it <= ' ' }) -> {
                errorsnackbar(resources.getString(R.string.errorpass), true)
                false
            }
            else -> {
                true
            }
        }
    }




    private fun login() {
        if (validarLogin()) {
            MostrarBarra(resources.getString(R.string.wait))

            val email = usua.text.toString().trim { it <= ' ' }
            val password = passw.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    Hideprogress()

                    if (task.isSuccessful) {
                        errorsnackbar("Ingreso exitoso.", false)

                    } else {
                        errorsnackbar(task.exception!!.message.toString(), true)
                    }

                }
        }
    }
}