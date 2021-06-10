package com.store.secondlife.model

import java.io.Serializable
import java.util.*

class Producto():Serializable {
    //lateinit var key:String
    lateinit var codigo:String
    lateinit var marca:String
    lateinit var modelo:String
    lateinit var descripcion:String
    lateinit var observacion:String
    lateinit var fec_compra:Date
    var stock:Int=0
    var precio:Double=0.0
    lateinit var imagen:String
    var calidad:Double=0.0
    var estado:Int=0
}