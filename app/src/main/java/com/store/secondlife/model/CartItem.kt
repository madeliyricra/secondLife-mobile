package com.store.secondlife.model

class CartItem {
    lateinit var key:String
    lateinit var nombre:String
    lateinit var imagen:String
    var precio=0.0
    var cantidad=0
    var total=precio*cantidad


    override fun toString(): String {
        return "CartItem(key='$key', nombre='$nombre', imagen='$imagen', precio=$precio, cantidad=$cantidad, total=$total)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartItem

        if (key != other.key) return false
        if (nombre != other.nombre) return false
        if (imagen != other.imagen) return false
        if (precio != other.precio) return false
        if (cantidad != other.cantidad) return false
        if (total != other.total) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + nombre.hashCode()
        result = 31 * result + imagen.hashCode()
        result = 31 * result + precio.hashCode()
        result = 31 * result + cantidad
        result = 31 * result + total.hashCode()
        return result
    }


}