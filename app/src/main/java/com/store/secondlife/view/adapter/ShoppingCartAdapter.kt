package com.store.secondlife.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.store.secondlife.model.Producto


class ShoppingCartAdapter:
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    var shoppingcart=ArrayList<Producto>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingCartAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ShoppingCartAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){

    }
}