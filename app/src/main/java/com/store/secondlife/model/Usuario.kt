package com.store.secondlife.model

import java.io.Serializable
import java.time.LocalDate
import java.util.*

class Usuario(): Serializable {
    lateinit var dni:String
    lateinit var nombre:String
    lateinit var apellido:String
    lateinit var telefono:String
    lateinit var fec_nacimiento:Date
    lateinit var usuario:String
    lateinit var contrasenia:String
    lateinit var email:String
    lateinit var rol:String
    var estado:Int = 0
}