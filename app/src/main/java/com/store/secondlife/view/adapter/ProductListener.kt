package com.store.secondlife.view.adapter

import com.store.secondlife.model.Producto

interface ProductListener {
    fun onProductClicked(product:Producto,positio:Int)

}