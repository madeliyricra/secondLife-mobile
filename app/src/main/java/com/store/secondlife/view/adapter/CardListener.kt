package com.store.secondlife.view.adapter

import com.store.secondlife.model11.Tarjeta


interface CardListener {
    fun onCardClicked(address: Tarjeta, position:Int)
}