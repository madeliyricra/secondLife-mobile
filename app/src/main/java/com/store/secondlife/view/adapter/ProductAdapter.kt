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
import com.store.secondlife.model.Producto

class ProductAdapter(val productListener:ProductListener):
    RecyclerView.Adapter<ProductAdapter.ViewHoldel>() {

    var listProduct=ArrayList<Producto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = ViewHoldel(LayoutInflater.from(parent.context).inflate(R.layout.item_product_1,
        parent, false))

    override fun getItemCount()=listProduct.size

    override fun onBindViewHolder(holder: ViewHoldel, position: Int) {
        val producto=listProduct[position] as Producto
        Glide.with(holder.itemView.context)
            .load(producto.imagen)
            .apply(RequestOptions.centerCropTransform())
            .into(holder.ivimagen)
        holder.tvcategoria.text=producto.marca
        holder.tvmodelo.text=producto.modelo
        holder.tvmarca.text=producto.marca
        holder.tvprecio.text= "S/. "+producto.precio.toString()

        holder.itemView.setOnClickListener {
            productListener.onProductClicked(producto, position)
        }
    }

    fun updateData(data: List<Producto>){
        listProduct.clear()
        listProduct.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHoldel(itemView:View):RecyclerView.ViewHolder(itemView) {
        val ivimagen=itemView.findViewById<ImageView>(R.id.iv_imagen)
        val tvcategoria=itemView.findViewById<TextView>(R.id.tv_categoria)
        val tvmodelo=itemView.findViewById<TextView>(R.id.tv_modelo)
        val tvmarca=itemView.findViewById<TextView>(R.id.tv_marca)
        val tvprecio=itemView.findViewById<TextView>(R.id.tv_precio)
    }
}