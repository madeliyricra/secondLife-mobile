package com.store.secondlife.view.adapter

import com.store.secondlife.model.Direccion

interface  AddressListener {
    fun onAddressClicked(address: Direccion, position:Int)
}