package com.store.secondlife.model11

import java.io.Serializable

class Tarjeta():Serializable {
    lateinit var tipo:String
    lateinit var  numero:String
    lateinit var fec_vencimiento: String
    var cvv:Int=0
}