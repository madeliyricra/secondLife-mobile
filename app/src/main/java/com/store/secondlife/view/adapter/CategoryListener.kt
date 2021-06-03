package com.store.secondlife.view.adapter

import com.store.secondlife.model.Categoria

interface CategoryListener {
    fun onCategoryClicked(category: Categoria, position:Int)
}