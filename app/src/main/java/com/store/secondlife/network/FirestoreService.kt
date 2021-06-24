package com.store.secondlife.network


import android.widget.Toast
import com.facebook.FacebookSdk
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.store.secondlife.model.Categoria
import com.store.secondlife.model.Direccion
import com.store.secondlife.model.Producto
import com.store.secondlife.model.Usuario
import com.store.secondlife.model11.Tarjeta
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import java.util.zip.ZipEntry
import kotlin.collections.ArrayList


class FirestoreService {

    val firebaseFirestore= FirebaseFirestore.getInstance()
    val settings= FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()

    init{
        firebaseFirestore.firestoreSettings==settings
    }
    /*----metodos con el firebase-------------*/
    /*-------------------usuario-----------------*/
    fun getUsuario(callback: Callback<Usuario>, user:String){
        firebaseFirestore.collection("usuario").document(user)
            .get()
            .addOnSuccessListener { result->
                if(result!=null){
                    val us=Usuario()
                    us.key= result.id
                    us.nombre=result.getString("nombre").toString()
                    us.apellido=result.getString("apellido").toString()
                    us.imagen=result.getString("imagen").toString()
                    us.email=result.getString("email").toString()
                    us.rol=result.getString("rol").toString()
                    us.dni=result.getString("dni").toString()
                    us.fec_nacimiento=result.getDate("fec_nacimiento") as Date
                    us.telefono=result.getString("telefono").toString()
                    us.usuario=result.getString("usuario").toString()
                    us.estado=result.getDouble("estado")!!.toInt()
                    callback.onSuccess(us)
                }else{
                    print("Usuario no encontrado")
                }
            }
    }

    fun getDireccion(callbak: Callback<List<Direccion>>, user: String){
        firebaseFirestore.collection("usuario").document(user)
            .collection("direccion")
            .orderBy("etiqueta")
            .get()
            .addOnSuccessListener { result ->
                val list:ArrayList<Direccion> = ArrayList<Direccion>()
                result.forEach { doc ->
                    val d=Direccion()
                    d.key=doc.id
                    d.nombre=doc.getString("nombre").toString()
                    d.etiqueta=doc.getString("etiqueta").toString()
                    d.icono=doc.getString("icono").toString()
                    d.latitud=doc.getString("latitud").toString()
                    d.longitud=doc.getString("longitud").toString()
                    d.referencia=doc.getString("referencia").toString()
                    list.add(d)
                    callbak.onSuccess(list)
                }
            }

    }

    fun getTarjeta(callbak: Callback<List<Tarjeta>>, user: String){
        firebaseFirestore.collection("usuario").document(user)
            .collection("tarjeta")
            .orderBy("tipo")
            .get()
            .addOnSuccessListener {result ->
                val list:ArrayList<Tarjeta> = ArrayList<Tarjeta>()
                result.forEach { doc ->
                    val t=Tarjeta()
                    t.key=doc.id
                    t.cvv=doc.getDouble("cvv")!!.toInt()
                    t.fec_vencimiento=doc.getString("fec_vencimiento").toString()
                    t.numero=doc.getString("numero").toString()
                    t.tipo=doc.getString("tipo").toString()
                    t.icono=doc.getString("icono").toString()
                    list.add(t)
                    callbak.onSuccess(list)
                }
            }
    }

    /*--------------------producto------------*/
    fun getProducto(callbak:Callback<List<Producto>>, cat:String){
        firebaseFirestore.collection("categoria").document(cat)
            .collection("producto")
            .orderBy("marca")
            .get()
            .addOnSuccessListener { result->
                val list:ArrayList<Producto> =ArrayList<Producto>()
                for(doc in result){
                    val p=Producto()
                    p.key=doc.id
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
                    list.add(p)
                    callbak.onSuccess(list)
                }
            }
    }

    fun getProductoRecommend(callbak:Callback<List<Producto>>){

        firebaseFirestore.collection("categoria")
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
                        .collection("producto")
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
        firebaseFirestore.collection("categoria")
            .get()
            .addOnSuccessListener { result->
                val lis:ArrayList<Categoria> =ArrayList<Categoria>()
                result.forEach { doc ->
                    val c:Categoria= Categoria()
                    c.key=doc.id
                    c.nombre=doc.getString("nombre").toString()
                    c.descripcion=doc.getString("descripcion").toString()
                    c.icon=doc.getString("icon").toString()
                    c.imagen=doc.getString("imagen").toString()
                    lis.add(c)
                }
                callbak.onSuccess(lis)
            }
    }

    /*-------------------descargar------------------------*/
    fun saveInformationUser(u: Usuario){
        val usuario=firebaseFirestore.collection("usuario")
            .document(u.key)
        usuario.update("apellido", u.apellido,
                            "dni", u.dni,
                            "nombre", u.nombre,
                            "telefono", u.telefono,
                            "usuario", u.usuario)
            .addOnSuccessListener {
                Toast.makeText(
                    FacebookSdk.getApplicationContext(),
                    "El usuario "+ u.usuario.toString() +" has sido actualizado", Toast.LENGTH_SHORT).show()
            }
    }
    fun addCard(t: Tarjeta, key:String) {
        /*val tarjeta= JSONArray("[\n" +
                "            {\n" +
                "                'tipo': '[" +
                "" + t.tipo+
                "                        ]',\n" +
                "                'numero': '[" +
                "" + t.numero+
                "                           ]',\n" +
                "                'fec_vencimiento': '[" +
                "" + t.fec_vencimiento+
                "                           ]',\n" +
                "                'cvv': '[" +
                "" + t.cvv+
                "                       ]'\n" +
                "            }\n" +
                "        ]")*/
        var  user= hashMapOf(
            "tipo" to t.tipo,
                "numero" to  t.numero,
            "fec_vencimiento" to  t.fec_vencimiento,
            "cvv" to  t.cvv
        )
        firebaseFirestore.collection("usuario").document(key)
            .collection("tarjeta").document().set(user)
            .addOnSuccessListener  {
                Toast.makeText(
                    FacebookSdk.getApplicationContext(),
                    "Se ha generado una nueva tarjeta", Toast.LENGTH_SHORT
                ).show()
            }
    }
}