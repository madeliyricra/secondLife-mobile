package com.store.secondlife.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.store.secondlife.R
import com.store.secondlife.model.Producto
import kotlinx.android.synthetic.main.detail_product_dialog.*


class DetailProductDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_product_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val producto=arguments?.getSerializable("producto") as Producto

        dataProducto(producto)

    }
    fun dataProducto(p:Producto){
        tv_marca.text=p.marca
        tv_precio.text= p.precio.toString()
         Glide.with(iv_imagen.context)
            .load(p.imagen)
            .apply(RequestOptions.fitCenterTransform())
            .into(iv_imagen)
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}