package com.warh.restaurante.dao

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.warh.restaurante.model.Producto
import com.warh.restaurante.utils.exceptions.FirebaseDatabaseRequestCancelledException

interface ProductDao {
    fun agregarProducto(producto: Producto)
    fun listarProductos(callback: (List<Producto>) -> Unit)
    fun obtenerProductoPorNombre(nombre: String)
    fun obtenerProductoPorId(id: String)
    fun borrarProducto(producto: Producto)
}

class ProductDaoImpl : ProductDao {
    companion object {
        val database = FirebaseDatabase.getInstance().reference
        const val PRODUCTS_PATH = "productos"
    }

    override fun agregarProducto(producto: Producto) {

        proximoIdProducto { id ->
            val mapaValores = hashMapOf<String, Any>()
            mapaValores["id"] = id.toString()
            mapaValores["categoria"] = producto.categoria
            mapaValores["nombre"] = producto.nombre
            mapaValores["precio"] = producto.precio
            mapaValores["imagen"] = producto.imagenLink

            database.child(PRODUCTS_PATH).child(id.toString()).setValue(mapaValores)
        }
    }

    private fun proximoIdProducto(callback: (Int) -> Unit) {
        database.child(PRODUCTS_PATH).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    callback.invoke(snapshot.childrenCount.toInt())
                } else {
                    callback.invoke(0)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw FirebaseDatabaseRequestCancelledException("proximoIdProducto")
            }
        })
    }

    override fun listarProductos(callback: (List<Producto>) -> Unit) {
        database.child(PRODUCTS_PATH).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val productosMap = snapshot.value as ArrayList<HashMap<String,*>>
                    val productos = mutableListOf<Producto>()

                    productosMap.forEach { producto ->
                        val id = producto["id"] as String
                        val nombre = producto["nombre"] as String
                        val precio = producto["precio"] as String
                        val imagen = producto["imagen"] as String
                        val categoria = producto["categoria"] as String

                        val productoTemp = Producto(id.toInt(), nombre, precio, imagen, categoria)
                        productos.add(productoTemp)
                    }

                    callback.invoke(productos)
                } else {
                    callback.invoke(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw FirebaseDatabaseRequestCancelledException("listarProductos")
            }
        })
    }

    override fun obtenerProductoPorNombre(nombre: String) {
        TODO("Not yet implemented")
    }

    override fun obtenerProductoPorId(id: String) {
        TODO("Not yet implemented")
    }

    override fun borrarProducto(producto: Producto) {
        TODO("Not yet implemented")
    }

}