package com.store.secondlife.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.store.secondlife.model.Direccion
import com.store.secondlife.network.Callback
import com.store.secondlife.network.FirestoreService
import java.lang.Exception

class AddressViewModel: ViewModel() {

    val firestoreService= FirestoreService()
    val listaAddress: MutableLiveData<List<Direccion>> = MutableLiveData()
    val isLoading= MutableLiveData<Boolean>()

    fun refresh(user: String){
        getAddressFromFirebase(user)
    }

    private fun getAddressFromFirebase(user: String) {
        firestoreService.getDireccion(object : Callback<List<Direccion>>{
            override fun onSuccess(result: List<Direccion>?) {
                listaAddress.postValue(result)
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