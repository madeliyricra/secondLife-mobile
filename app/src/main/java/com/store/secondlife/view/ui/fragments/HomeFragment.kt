package com.store.secondlife.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.store.secondlife.R
import com.store.secondlife.model.Categoria
import com.store.secondlife.model.Producto
import com.store.secondlife.view.adapter.*
import com.store.secondlife.viewmodel.CategoryViewModel
import com.store.secondlife.viewmodel.ProductHomeViewModel
import com.store.secondlife.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.rvCategory
import kotlinx.android.synthetic.main.fragment_product.*


class HomeFragment : Fragment(), CategoryListener, ProductListener {

    private lateinit var categoryAdapter: CategoryHomeAdapter
    private lateinit var viewModel: CategoryViewModel

    private lateinit var productoAdapter: ProductHomeAdapter
    private lateinit var productViewModel: ProductHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSeeSell.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        /*-------vista de categorias-----------------*/
        viewModel= ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        viewModel.refresh()

        categoryAdapter= CategoryHomeAdapter(this)

        rvCategory.apply{
            layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            adapter=categoryAdapter
        }

        viewModel.listaCategory.observe(viewLifecycleOwner, Observer<List<Categoria>>{ categoria->
            categoryAdapter.updateData(categoria)
        })
        /*--------------vista de producto------------------------*/
        productViewModel=ViewModelProviders.of(this).get(ProductHomeViewModel::class.java)
        productViewModel.refresh()
        productoAdapter= ProductHomeAdapter(this)

        rvRecommend.apply {
            layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            adapter=productoAdapter
        }
        productViewModel.listaProducto.observe(viewLifecycleOwner,Observer<List<Producto>> { producto ->
            productoAdapter.updateData(producto)
        })

    }
    override fun onCategoryClicked(category: Categoria, position: Int) {
        var bundle= bundleOf("categoria" to category)
        findNavController().navigate(R.id.productFragment, bundle)

    }

    override fun onProductClicked(product: Producto, positio: Int) {
        var bundle= bundleOf("producto" to product)
        bundle.putString("categoria","categoria")
        findNavController().navigate(R.id.detailProductDialogFragment, bundle)
    }
}