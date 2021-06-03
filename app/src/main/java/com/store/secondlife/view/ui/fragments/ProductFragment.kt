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
import com.store.secondlife.R
import com.store.secondlife.model.Categoria
import com.store.secondlife.model.Producto
import com.store.secondlife.view.adapter.ProductAdapter
import com.store.secondlife.view.adapter.ProductListener
import com.store.secondlife.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product.*


class ProductFragment : Fragment(), ProductListener {

    private lateinit var productoAdapter: ProductAdapter
    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val category=arguments?.getSerializable("categoria") as Categoria
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(ProductViewModel::class.java)
        viewModel.refresh(category.key)
        productoAdapter= ProductAdapter(this)

        rvProducto.apply {
            layoutManager=GridLayoutManager(context,2)
            adapter=productoAdapter
        }
        viewModel.listaProducto.observe(viewLifecycleOwner,Observer<List<Producto>> { producto ->
            producto.let {
                productoAdapter.updateData(producto)
            }
        })
    }

    override fun onProductClicked(product: Producto, positio: Int) {
        var bundle= bundleOf("producto" to product)
        findNavController().navigate(R.id.profileInformationDialogFragment, bundle)
    }
}