package com.warh.restaurante.viewmodel

import androidx.lifecycle.ViewModel
import com.warh.restaurante.model.Producto
import com.warh.restaurante.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    fun agregarProducto(producto: Producto) = productRepository.agregarProducto(producto)

    fun listarProductos(callback: (List<Producto>) -> Unit) = productRepository.listarProductos(callback)

}