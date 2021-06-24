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
import com.store.secondlife.model.Direccion
import com.store.secondlife.model11.Tarjeta

class CardAdapter (val user:String, val cardlistener:CardListener):
    RecyclerView.Adapter<CardAdapter.ViewHolder>(){

    val listCard=ArrayList<Tarjeta>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
       = CardAdapter.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_card,
            parent, false
        )
    )

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {

        val card=listCard[position] as Tarjeta

        Glide.with(holder.itemView.context)
            .load(card.icono)
            .apply(RequestOptions.fitCenterTransform())
            .into(holder.ivicono)
        holder.tvnum.text=card.numero.substring(card.numero.length-3, card.numero.length)
    }

    override fun getItemCount() = listCard.size

    fun updateData(data: List<Tarjeta>){
        listCard.clear()
        listCard.addAll(data)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivicono=itemView.findViewById<ImageView>(R.id.iv_icon_card)
        val tvnum=itemView.findViewById<TextView>(R.id.tv_num_card)
    }


}