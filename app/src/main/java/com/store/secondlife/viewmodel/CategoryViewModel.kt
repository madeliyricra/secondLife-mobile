package com.store.secondlife.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.store.secondlife.model.Categoria
import com.store.secondlife.network.Callback
import com.store.secondlife.network.FirestoreService
import java.lang.Exception

class CategoryViewModel: ViewModel() {
    val firestoreService= FirestoreService()
    val listaCategory: MutableLiveData<List<Categoria>> = MutableLiveData()
    val isLoading= MutableLiveData<Boolean>()

    fun refresh(){
        getCategoryFromFirebase()
    }

    private fun getCategoryFromFirebase() {
        firestoreService.getCategoria(object : Callback<List<Categoria>> {
            override fun onSuccess(result: List<Categoria>?) {
                listaCategory.postValue(result)
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