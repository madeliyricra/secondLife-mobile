package com.store.secondlife.view.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.common.internal.ConnectionErrorMessages
import com.google.android.material.snackbar.Snackbar
import com.store.secondlife.R
import kotlinx.android.synthetic.main.dialog_progressbar.*

open class BaseActivity : AppCompatActivity() {

    private lateinit var mprogressdialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
    fun errorsnackbar(message:String, errorMessage: Boolean){
        val snackBar=
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView =snackBar.view

        if (errorMessage){
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }

    fun MostrarBarra(text:String){
        mprogressdialog= Dialog(this)
        mprogressdialog.setContentView(R.layout.dialog_progressbar)
        mprogressdialog.progressText.text = text
        mprogressdialog.setCancelable(false)
        mprogressdialog.setCanceledOnTouchOutside(false)
        mprogressdialog.show()
    }

    fun Hideprogress(){
        mprogressdialog.dismiss()
    }

}