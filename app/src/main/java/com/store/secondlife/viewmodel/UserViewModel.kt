package com.store.secondlife.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.store.secondlife.model.Usuario
import com.store.secondlife.network.Callback
import com.store.secondlife.network.FirestoreService
import java.lang.Exception

class UserViewModel:ViewModel() {
    val firestoreService= FirestoreService()
    val usuario: MutableLiveData<Usuario> = MutableLiveData()
    val isLoading= MutableLiveData<Boolean>()

    fun refresh(user:String){
        getUserFromFirebase(user)
    }

    private fun getUserFromFirebase(user: String) {
        firestoreService.getUsuario(object :Callback<Usuario>
        {
            override fun onSuccess(result: Usuario?) {
                usuario.postValue(result)
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