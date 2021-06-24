package com.store.secondlife.model11

import java.io.Serializable

class Tarjeta():Serializable {
    lateinit var key:String
    lateinit var tipo:String
    lateinit var  numero:String
    lateinit var fec_vencimiento: String
    lateinit var icono: String
    var cvv:Int=0
}