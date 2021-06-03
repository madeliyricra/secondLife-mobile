package com.store.secondlife.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.store.secondlife.model.Categoria
import com.store.secondlife.model.Producto

const val PRODUCTO_COLLECTION_NAME="producto"
const val  CATEGORIA_COLLECTION_NAME="categoria"

class FirestoreService {
    val firebaseFirestore= FirebaseFirestore.getInstance()
    val settings= FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()

    init{
        firebaseFirestore.firestoreSettings==settings
    }
    fun getProducto(callbak:Callback<List<Producto>>){
        firebaseFirestore.collection("categoria").document("T8VT5QGGnqWXXAq3Cnf4")
            .collection(PRODUCTO_COLLECTION_NAME)
            .orderBy("marca")
            .get()
            .addOnSuccessListener { result->
                for(doc in result){
                    val list=result.toObjects(Producto::class.java)
                    callbak.onSuccess(list)
                    break
                }
            }
    }
    fun getCategoria(callbak: Callback<List<Categoria>>){
        firebaseFirestore.collection(CATEGORIA_COLLECTION_NAME)
            .orderBy("mombre")
            .get()
            .addOnSuccessListener { result->
                for(doc in result){
                    val list=result.toObjects(Categoria::class.java)
                    callbak.onSuccess(list)
                    break
                }
            }
    }
}