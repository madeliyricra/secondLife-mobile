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
import com.store.secondlife.model.Categoria

class CategoryHomeAdapter(val categoryListener:CategoryListener):
    RecyclerView.Adapter<CategoryHomeAdapter.ViewHolder>() {

    var listCategory=ArrayList<Categoria>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        =CategoryHomeAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_category_home,
                parent, false
            )
        )

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val ivimagen=itemView.findViewById<ImageView>(R.id.tv_imagen)
        val tvnombre=itemView.findViewById<TextView>(R.id.tv_nombre)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category=listCategory[position] as Categoria
        Glide.with(holder.itemView.context)
            .load(category.icon)
            .apply(RequestOptions.fitCenterTransform())
            .into(holder.ivimagen)
        holder.tvnombre.text=category.nombre

        holder.itemView.setOnClickListener {
            categoryListener.onCategoryClicked(category, position)
        }
    }

    fun updateData(data:List<Categoria>){
        listCategory.clear()
        listCategory.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount()=listCategory.size-1
}