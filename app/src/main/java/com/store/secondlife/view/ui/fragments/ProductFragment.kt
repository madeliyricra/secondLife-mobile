package com.store.secondlife.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.store.secondlife.R
import com.store.secondlife.model.Categoria
import com.store.secondlife.model.Producto
import com.store.secondlife.view.adapter.*
import com.store.secondlife.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product.*


class ProductFragment : Fragment(), ProductListener{

    private lateinit var producto1Adapter: Product1Adapter
    private lateinit var producto2Adapter: Product2Adapter

    private lateinit var productViewModel: ProductViewModel

    private var category= Categoria()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category=arguments?.getSerializable("categoria") as Categoria

        Glide.with(view.context)
            .load(category.imagen)
            .apply(RequestOptions.fitCenterTransform())
            .into(iv_imagencategoria)
        tv_nombrecategoria.text=category.nombre
        tv_descripcategoria.text=category.descripcion
        ib_product1.setImageResource(R.drawable.ic_view_list_square_select)
       /*------------productos por categoria-----------------*/
        viewProduct1()
        /*------------estilos de producto--------------*/
        viewProduct()
    }

    private fun viewProduct() {

        ib_product1.setOnClickListener {
            ib_product1.setImageResource(R.drawable.ic_view_list_square_select)
            ib_product2.setImageResource(R.drawable.ic_view_list)
            viewProduct1()
        }
        ib_product2.setOnClickListener {
            ib_product2.setImageResource(R.drawable.ic_view_list_select)
            ib_product1.setImageResource(R.drawable.ic_views_list_square)
            productViewModel=ViewModelProviders.of(this).get(ProductViewModel::class.java)
            productViewModel.refresh(category.key)
            producto2Adapter= Product2Adapter(category.nombre, this)

            rvProducto.apply {
                layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter=producto2Adapter
            }
            productViewModel.listaProducto.observe(viewLifecycleOwner,
                Observer<List<Producto>> { producto ->
                producto2Adapter.updateData(producto)
            })
        }
    }

    private fun viewProduct1() {
        productViewModel=ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.refresh(category.key)
        producto1Adapter= Product1Adapter(category.nombre,this)

        rvProducto.apply {
            layoutManager=GridLayoutManager(context,3)
            adapter=producto1Adapter
        }
        productViewModel.listaProducto.observe(viewLifecycleOwner,Observer<List<Producto>> { producto ->
            producto1Adapter.updateData(producto)
        })
    }

    override fun onProductClicked(product: Producto, position: Int) {
        var bundle= bundleOf("producto" to product)
        bundle.putString("categoria",category.nombre)
        findNavController().navigate(R.id.detailProductDialogFragment, bundle)
    }


}