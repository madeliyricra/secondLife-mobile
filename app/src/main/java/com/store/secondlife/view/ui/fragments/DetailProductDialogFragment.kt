package com.store.secondlife.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.store.secondlife.R
import com.store.secondlife.model.Producto
import kotlinx.android.synthetic.main.detail_product_dialog.*
import kotlinx.android.synthetic.main.item_category.*
import kotlinx.android.synthetic.main.item_category.iv_imagen
import kotlinx.android.synthetic.main.item_category.tv_nombre


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
        val categoria = arguments?.getString("categoria") as String
        dataProducto(producto, categoria)

        dp_retornar.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }
    }
    fun dataProducto(p:Producto, categoria:String){
        tv_marca.text=p.marca
        tv_precio.text= p.precio.toString()
         Glide.with(iv_imagen.context)
            .load(p.imagen)
            .apply(RequestOptions.fitCenterTransform())
            .into(iv_imagen)
        tv_cantidad.text=p.stock.toString()
        tv_nombre.text=p.modelo
        tv_observacion.text=p.observacion
        tv_caracteristica.text=p.descripcion
        tv_categoria.text=categoria
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}