package com.store.secondlife.network


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.store.secondlife.model.Categoria
import com.store.secondlife.model.Producto
import java.util.*
import kotlin.collections.ArrayList

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

    fun getProductoRecommend(callbak:Callback<List<Producto>>){

        firebaseFirestore.collection(CATEGORIA_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->

                val lis: ArrayList<Categoria> = ArrayList<Categoria>()
                result.forEach { doc ->
                    val c: Categoria = Categoria()
                    c.key = doc.id
                    c.nombre = doc.getString("nombre").toString()
                    c.descripcion = doc.getString("descripcion").toString()
                    c.imagen = doc.getString("imagen").toString()
                    lis.add(c)
                }
                val prod:ArrayList<Producto> =ArrayList<Producto>()
                lis.forEach { c ->
                    firebaseFirestore.collection("categoria").document(c.key)
                        .collection(PRODUCTO_COLLECTION_NAME)
                        .get()
                        .addOnSuccessListener { result ->

                            for (doc in result) {
                                val p=Producto()
                                //p.key=doc.id
                                p.calidad= doc.getDouble("calidad") as Double
                                p.codigo=doc.getString("codigo").toString()
                                p.estado= doc.getDouble("estado")!!.toInt()
                                p.fec_compra=doc.getDate("fec_compra") as Date
                                p.marca=doc.getString("marca").toString()
                                p.modelo=doc.getString("modelo").toString()
                                p.observacion=doc.getString("observacion").toString()
                                p.precio=doc.getDouble("precio") as Double
                                p.stock= doc.getDouble("stock")!!.toInt()
                                p.descripcion=doc.getString("descripcion").toString()
                                p.imagen=doc.getString("imagen").toString()
                                prod.add(p)

                            }
                            val pro:ArrayList<Producto> =ArrayList<Producto>()
                            var num=0
                            for(p in prod.sortedByDescending { producto ->  producto.calidad }){
                              if(num<6){
                                  num++
                                  pro.add(p)
                              }
                            }
                            callbak.onSuccess(pro)
                        }
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
                }
                callbak.onSuccess(lis)
            }
    }

}