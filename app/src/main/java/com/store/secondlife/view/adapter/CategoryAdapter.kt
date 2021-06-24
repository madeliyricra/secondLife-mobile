package com.store.secondlife.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.store.secondlife.R
import com.store.secondlife.model.Categoria


class CategoryAdapter(val categoryListener:CategoryListener):
RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var listCategory=ArrayList<Categoria>()
    var item= R.layout.item_category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        =ViewHolder(LayoutInflater.from(parent.context).inflate( item,
                            parent,false))

    override fun getItemCount()=listCategory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category=listCategory[position] as Categoria
        Glide.with(holder.itemView.context)
            .load(category.imagen)
            .into(holder.ivimagen)
        holder.tvnombre.text=category.nombre
        holder.tvdescripcion.text=category.descripcion

        holder.itemView.setOnClickListener {
            categoryListener.onCategoryClicked(category, position)
        }
    }

    fun updateData(data:List<Categoria>){
        listCategory.clear()
        listCategory.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)  {
        val ivimagen=itemView.findViewById<ImageView>(R.id.iv_imagen)
        val tvnombre=itemView.findViewById<TextView>(R.id.tv_nombre)
        val tvdescripcion=itemView.findViewById<TextView>(R.id.tv_descripcion)
    }

}