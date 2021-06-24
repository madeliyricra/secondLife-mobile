package com.store.secondlife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.JsonObject
import com.store.secondlife.model.CartItem
import com.store.secondlife.model.Categoria
import com.store.secondlife.model.Producto
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity  : AppCompatActivity()  {

    public final var lista:MutableList<CartItem>?= ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setActionBar(findViewById(R.id.toolbar_main))
        configNav()
        init()
    }
    fun configNav(){
        NavigationUI.setupWithNavController(bnvMenu, Navigation.findNavController(this, R.id.fragContent))
    }
    private fun init(){

    }

}