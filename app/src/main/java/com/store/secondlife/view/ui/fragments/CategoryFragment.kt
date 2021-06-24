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
import androidx.recyclerview.widget.LinearLayoutManager
import com.store.secondlife.R
import com.store.secondlife.model.Categoria
import com.store.secondlife.view.adapter.CategoryAdapter
import com.store.secondlife.view.adapter.CategoryListener
import com.store.secondlife.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment(), CategoryListener {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var viewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        viewModel.refresh()

        categoryAdapter= CategoryAdapter(this)

        rvCategory.apply{
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter=categoryAdapter
        }

        viewModel.listaCategory.observe(viewLifecycleOwner, Observer<List<Categoria>>{ categoria->
            categoryAdapter.updateData(categoria)
        })


    }

    override fun onCategoryClicked(category: Categoria, position: Int) {
        var bundle= bundleOf("categoria" to category)
        findNavController().navigate(R.id.productFragment, bundle)

    }

}