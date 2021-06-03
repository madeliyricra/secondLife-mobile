package com.store.secondlife.view.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import com.google.firebase.firestore.FirebaseFirestore
import com.store.secondlife.MainActivity
import com.store.secondlife.R
import com.store.secondlife.model.Direccion
import com.store.secondlife.model.Producto
import com.store.secondlife.model11.Tarjeta
import kotlinx.android.synthetic.main.activity_splashscreen.*
import org.json.JSONArray
import org.json.JSONObject


class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        //progress bar
        val progressBar = findViewById<View>(R.id.spin_kit) as ProgressBar
        val ThreeBounce: Sprite = Wave()
        progressBar.indeterminateDrawable = ThreeBounce

        var boolean=false
        //animacion
        val animation=AnimationUtils.loadAnimation(this, R.anim.animation)
        ivLogoSecondLife.startAnimation(animation)
        val intent=Intent(this, MainActivity::class.java)

        animation.setAnimationListener(object :Animation.AnimationListener{

            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                startActivity(intent)
                finish()
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

    }

}