package com.store.secondlife.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.store.secondlife.R
import com.store.secondlife.model.CartItem

class CartAdapter(private val carrito: ArrayList<CartItem> = ArrayList(),
                  private val click:Any):
                RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    var items: ArrayList<CartItem>?= null
    var cartViewHolder:CardAdapter.ViewHolder?=null
    init{
        this.items=items
    }

    class CartViewHolder(view: View, listener: CartListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener{

        val listener:CartListener?=null

        val tv_nombre=view.findViewById<TextView>(R.id.tv_nombre)
        val iv_imagen=view.findViewById<ImageView>(R.id.iv_imagen)
        val tv_precio=view.findViewById<TextView>(R.id.tv_precio)
        val tv_cantidad=view.findViewById<TextView>(R.id.tv_cantidad)
        override fun onClick(v: View?) {
            listener?.onClick(v!!, adapterPosition)
        }

    }

    /*fun agregarCarrito(c: Cart){
        carrito.add(c)
        notifyItemInserted(itemCount)
    }

    fun getCarrito(posicion: Int) : Cart{
        return carrito[posicion]
    }

    fun eliminarCarrito(posicion: Int){
        carrito.removeAt(posicion)
        notifyItemRemoved(posicion)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_cart, parent, false)
        return CartViewHolder(view, click as CartListener)!!
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val items=items?.get(position)
        holder.tv_nombre?.text=items?.nombre
        Glide.with(holder.itemView.context)
            .load(items?.imagen)
            .apply(RequestOptions.fitCenterTransform())
            .into(holder.iv_imagen)
        holder.tv_precio?.text=items?.precio.toString()
        holder.tv_cantidad?.text=items?.cantidad.toString()

    }

    override fun getItemCount()=carrito.size

}



class CartAdapterListener {

}
