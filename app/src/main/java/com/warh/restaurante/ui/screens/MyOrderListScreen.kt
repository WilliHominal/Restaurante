package com.warh.restaurante.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.warh.restaurante.R
import com.warh.restaurante.model.Pedido
import com.warh.restaurante.model.Producto
import com.warh.restaurante.model.ProductoComprado
import com.warh.restaurante.ui.components.CustomTopBar
import com.warh.restaurante.ui.components.OrderCardView
import java.util.*

@Composable
fun MyOrderListScreen(
    navController: NavController
) {
    val context = LocalContext.current

    //TODO Cambiar datasource de los datos
    val productos = listOf(
        Producto(0, "PRODUCTO 1", "100", "", "PLATO PRINCIPAL"),
        Producto(1, "PRODUCTO 2", "200", "", "ENTRADA"),
        Producto(2, "PRODUCTO 3", "400", "", "BEBIDA"),
    )
    val pedidos = listOf(
        Pedido(0, listOf(
            ProductoComprado(productos[0], "85", 2),
            ProductoComprado(productos[1], "150", 6),
        ), "ENTREGADO", Date().time - 24*60*60*1000),
        Pedido(1, listOf(
            ProductoComprado(productos[0], "85", 2),
            ProductoComprado(productos[1], "180", 3),
            ProductoComprado(productos[2], "250", 1),
        ), "EN CAMINO", Date().time),
    )
    //TODO ordenar pedidos por timestamp descendente

    Scaffold(
        topBar = {
            CustomTopBar(
                titulo = context.getString(R.string.TITULO_MIS_PEDIDOS),
                accionBotonIzquierda = { navController.popBackStack() },
                accionBotonDerecha = { /*TODO accion click usuario topbar*/ },
                usuarioLogueado = true,
                permiteVolver = true,
                usuarioImagenLink = "https://picsum.photos/100" //TODO cambiar por foto perfil usuario
            )
        },
        backgroundColor = MaterialTheme.colors.primary.copy(0.1f)
    ) { scaffoldPadding ->
        LazyColumn(
            Modifier
                .padding(scaffoldPadding)
                .fillMaxWidth()
                .padding(top = 10.dp)
        ){
            items(pedidos){
                OrderCardView(it) {/*TODO accion click pedido card view*/}
            }
        }
    }
}