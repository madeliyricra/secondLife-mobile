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
import com.store.secondlife.view.adapter.CategoryAdapter
import com.store.secondlife.view.adapter.CategoryHomeAdapter
import com.store.secondlife.view.adapter.CategoryListener
import com.store.secondlife.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.rvCategory


class HomeFragment : Fragment(), CategoryListener {


    private lateinit var categoryAdapter: CategoryHomeAdapter
    private lateinit var viewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*-------vista de categorias-----------------*/
        viewModel= ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        viewModel.refresh()

        categoryAdapter= CategoryHomeAdapter(this)

        rvCategory.apply{
            layoutManager= GridLayoutManager(context,3)
            adapter=categoryAdapter
        }

        viewModel.listaCategory.observe(viewLifecycleOwner, Observer<List<Categoria>>{ categoria->
            categoryAdapter.updateData(categoria)
        })
        /*--------------------------------------*/
    }
    override fun onCategoryClicked(category: Categoria, position: Int) {
        var bundle= bundleOf("categoria" to category)
        findNavController().navigate(R.id.productFragment, bundle)

    }
}