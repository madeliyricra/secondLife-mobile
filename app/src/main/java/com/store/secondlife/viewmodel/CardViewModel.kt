package com.store.secondlife.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.store.secondlife.model11.Tarjeta
import com.store.secondlife.network.Callback
import com.store.secondlife.network.FirestoreService
import java.lang.Exception

class CardViewModel: ViewModel() {
    val firestoreService= FirestoreService()
    val listaTarjeta: MutableLiveData<List<Tarjeta>> = MutableLiveData()
    val isLoading= MutableLiveData<Boolean>()

    fun refresh(user: String){
        getCardFromFirebase(user)
    }

    private fun getCardFromFirebase(user: String) {
        firestoreService.getTarjeta(object : Callback<List<Tarjeta>> {
            override fun onSuccess(result: List<Tarjeta>?) {
                listaTarjeta.postValue(result)
                processFinished()
            }
            override fun onFailed(exception: Exception) {
                processFinished()
            }

        }, user)
    }

    fun processFinished(){
        isLoading.value=true
    }
}