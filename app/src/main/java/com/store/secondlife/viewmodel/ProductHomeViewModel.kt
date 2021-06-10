package com.store.secondlife.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.store.secondlife.model.Producto
import com.store.secondlife.network.Callback
import com.store.secondlife.network.FirestoreService
import java.lang.Exception

class ProductHomeViewModel: ViewModel() {
    val firestoreService= FirestoreService()
    val listaProducto: MutableLiveData<List<Producto>> = MutableLiveData()
    val isLoading= MutableLiveData<Boolean>()

    fun refresh(){
        getProductFromFirebase()
    }

    private fun getProductFromFirebase() {
        firestoreService.getProductoRecommend(object : Callback<List<Producto>> {
            override fun onSuccess(result: List<Producto>?) {
                listaProducto.postValue(result)
                processFinished()
            }
            override fun onFailed(exception: Exception) {
                processFinished()
            }

        })
    }

    fun processFinished(){
        isLoading.value=true
    }
}