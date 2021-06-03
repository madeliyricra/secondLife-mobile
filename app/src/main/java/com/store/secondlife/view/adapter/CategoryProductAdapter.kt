package com.store.secondlife.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.store.secondlife.R
import com.store.secondlife.model.Categoria

class CategoryProductAdapter (val categoryListener:CategoryListener):
    RecyclerView.Adapter<CategoryProductAdapter.ViewHoldel>() {

    var listCategory=ArrayList<Categoria>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            =CategoryProductAdapter.ViewHoldel(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_category_product,
            parent, false
        )
    )

    class ViewHoldel(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvnombre=itemView.findViewById<TextView>(R.id.tv_nombre)
    }

    override fun onBindViewHolder(holder: ViewHoldel, position: Int) {
        val category=listCategory[position] as Categoria

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

    override fun getItemCount()=listCategory.size

}