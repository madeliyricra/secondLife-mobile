package com.store.secondlife.model

import java.io.Serializable

class Categoria() : Serializable {
    var key=""
    lateinit var nombre:String
    lateinit var descripcion:String
    lateinit var icon:String
    lateinit var imagen:String
}