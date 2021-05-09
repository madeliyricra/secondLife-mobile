package com.store.secondlife.view.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.store.secondlife.MainActivity
import com.store.secondlife.R
import kotlinx.android.synthetic.main.activity_splashscreen.*


class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        //progress bar
        val progressBar = findViewById<View>(R.id.spin_kit) as ProgressBar
        val ThreeBounce: Sprite = ThreeBounce()
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
        //startActivity(intent)
        /*ivLogoSecondLife.setOnClickListener{
            boolean=animation(ivLogoSecondLife, R.raw.name, boolean)
        }*/
    }
    /*private fun animation(imageView: LottieAnimationView, animation: Int,
                            anim:Boolean):Boolean{
        if(!anim){
            imageView.setAnimation(animation)
            imageView.playAnimation()
        }else{
            imageView.setImageResource(R.drawable.logo_entry)
        }
        return !anim;
    }*/

}