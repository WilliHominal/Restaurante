package com.warh.restaurante.model

data class Pedido(val id: Int, val productos: List<ProductoComprado>, val estado: String, val timestamp: Long)