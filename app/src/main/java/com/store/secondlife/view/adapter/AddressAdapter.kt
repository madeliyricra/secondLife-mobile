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

class AddressAdapter(val user:String, val addressListener: AddressListener):
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    val listAddress= ArrayList<Direccion>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        =ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_address,
                    parent, false))

    override fun onBindViewHolder(holder: AddressAdapter.ViewHolder, position: Int) {
        val address=listAddress[position] as Direccion

        Glide.with(holder.itemView.context)
            .load(address.icono)
            .apply(RequestOptions.fitCenterTransform())
            .into(holder.ivicon)
        holder.tvaddress.text=address.nombre
        holder.tvetiqueta.text= address.etiqueta.capitalize()

        holder.itemView.setOnClickListener {
            addressListener.onAddressClicked(address, position)
        }
    }

    override fun getItemCount() = listAddress.size

    fun updateData(data: List<Direccion>){
        listAddress.clear()
        listAddress.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val ivicon=itemView.findViewById<ImageView>(R.id.iv_icono)
        val tvetiqueta=itemView.findViewById<TextView>(R.id.tv_etiqueta)
        val tvaddress=itemView.findViewById<TextView>(R.id.tv_address)
    }
}