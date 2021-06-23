package com.store.secondlife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.store.secondlife.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity  : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setActionBar(findViewById(R.id.toolbar_main))
        configNav()
 
    }
    fun configNav(){
        NavigationUI.setupWithNavController(bnvMenu, Navigation.findNavController(this, R.id.fragContent))
    }

    private fun checkCurrentUser() {
        // [START check_current_user]
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
        // [END check_current_user]
    }


}