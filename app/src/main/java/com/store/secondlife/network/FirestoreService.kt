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
    /*----metodos con el firebase-------------*/
    fun getProducto(callbak:Callback<List<Producto>>, cat:String){
        firebaseFirestore.collection("categoria").document(cat)
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
            .get()
            .addOnSuccessListener { result->
                val lis:ArrayList<Categoria> =ArrayList<Categoria>()
                result.forEach { doc ->
                    val c:Categoria= Categoria()
                    c.key=doc.id
                    c.nombre=doc.getString("nombre").toString()
                    c.descripcion=doc.getString("descripcion").toString()
                    c.imagen=doc.getString("imagen").toString()
                    lis.add(c)

                    val cod=doc.id
                }
                callbak.onSuccess(lis)
            }
    }

}