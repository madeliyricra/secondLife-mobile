package com.store.secondlife.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.store.secondlife.R
import com.store.secondlife.model.Categoria
import com.store.secondlife.model.Producto
import com.store.secondlife.view.adapter.CategoryListener
import com.store.secondlife.view.adapter.CategoryProductAdapter
import com.store.secondlife.view.adapter.ProductAdapter
import com.store.secondlife.view.adapter.ProductListener
import com.store.secondlife.viewmodel.CategoryViewModel
import com.store.secondlife.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product.*


class ProductFragment : Fragment(), ProductListener, CategoryListener {

    private lateinit var productoAdapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel

    private lateinit var categoryAdapter:CategoryProductAdapter
    private  lateinit var categoryViewModel:CategoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category=arguments?.getSerializable("categoria") as Categoria
        /*---------lista de categorias--------------------*/
        categoryViewModel=ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        categoryViewModel.refresh()
        categoryAdapter= CategoryProductAdapter(this)

        rv_categoria.apply {
            layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            adapter=categoryAdapter
        }
        categoryViewModel.listaCategory.observe(viewLifecycleOwner,Observer<List<Categoria>> { categoria ->
            categoria.let {
                categoryAdapter.updateData(categoria)
            }
        })
       /*------------productos por categoria-----------------*/
        productViewModel=ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.refresh(category.key)
        productoAdapter= ProductAdapter(this)

        rvProducto.apply {
            layoutManager=GridLayoutManager(context,2)
            adapter=productoAdapter
        }
        productViewModel.listaProducto.observe(viewLifecycleOwner,Observer<List<Producto>> { producto ->
            producto.let {
                productoAdapter.updateData(producto)
            }
        })
    }

    override fun onProductClicked(product: Producto, positio: Int) {
        var bundle= bundleOf("producto" to product)
        findNavController().navigate(R.id.detailProductDialogFragment, bundle)
    }

    override fun onCategoryClicked(category: Categoria, position: Int) {
        TODO("Not yet implemented")
    }
}