package com.warh.restaurante.repository

import com.warh.restaurante.dao.ProductDao
import com.warh.restaurante.model.Producto
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    fun agregarProducto(producto: Producto) = productDao.agregarProducto(producto)
    fun listarProductos(callback: (List<Producto>) -> Unit) = productDao.listarProductos(callback)
}