package com.warh.restaurante.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.warh.restaurante.R
import com.warh.restaurante.model.Producto
import com.warh.restaurante.model.ProductoComprado
import com.warh.restaurante.ui.components.CustomTopBar
import com.warh.restaurante.ui.components.ProductSearchView

@Composable
fun NewOrderScreen(
    navController: NavController
) {
    val context = LocalContext.current

    var textoBusqueda by remember { mutableStateOf("") }

    //TODO obtener lista de productos
    val listaProductos = listOf(
        Producto(0, "Aproducto", "205", "https://picsum.photos/300", "CAT A"),
        Producto(1, "AAproducto", "50", "https://picsum.photos/200", "CAT B"),
        Producto(2, "Bproducto", "350", "https://picsum.photos/350", "CAT C"),
        Producto(3, "Aproducto2", "50", "https://picsum.photos/200", "CAT B"),
        Producto(4, "Aproducto3", "350", "https://picsum.photos/350", "CAT C"),
        Producto(5, "Aproducto4", "350", "https://picsum.photos/350", "CAT C"),
        Producto(6, "Aproducto5", "350", "https://picsum.photos/350", "CAT C"),
        Producto(7, "Aproducto6", "350", "https://picsum.photos/350", "CAT C"),
        Producto(8, "Aproducto7", "350", "https://picsum.photos/350", "CAT C"),
        Producto(9, "Aproducto8", "350", "https://picsum.photos/350", "CAT C"),
        Producto(10, "Aproducto9", "350", "https://picsum.photos/350", "CAT C"),
        Producto(11, "Aproducto10", "350", "https://picsum.photos/350", "CAT C"),
    )

    val carritoCompra = remember { mutableStateListOf<ProductoComprado>() }

    Scaffold(
        backgroundColor = MaterialTheme.colors.primary.copy(0.1f),
        topBar = {
            CustomTopBar(
                titulo = context.getString(R.string.TITULO_NUEVO_PEDIDO),
                permiteVolver = true,
                accionBotonIzquierda = { navController.popBackStack() },
                usuarioImagenLink = "",
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {/*TODO*/}, backgroundColor = MaterialTheme.colors.primaryVariant) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = context.getString(R.string.BOTON_CONTINUAR),
                        modifier = Modifier
                            .padding(start = 8.dp, end = 5.dp)
                    )

                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Continuar"
                    )
                }
            }
        },
    ) { scaffoldPadding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = 25.dp).padding(top = 15.dp)
                .fillMaxSize()
        ) {
            item {
                ProductSearchView(
                    textoBusqueda = textoBusqueda,
                    accionLimpiar = { textoBusqueda = "" },
                    placeholder = context.getString(R.string.PLACEHOLDER_NOMBRE_PRODUCTO),
                    accionCambioValor = { textoBusqueda = it }
                )
            }

            item {
                AnimatedVisibility(
                    visible = textoBusqueda.isNotBlank(),
                    enter = expandVertically(
                        animationSpec = tween(100, easing = FastOutLinearInEasing),
                        expandFrom = Alignment.Top,
                        initialHeight = { 0 },
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(100, easing = FastOutLinearInEasing),
                        shrinkTowards = Alignment.Top,
                        targetHeight = { 0 },
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        LazyColumn(
                            Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            items(listaProductos.filter { producto ->
                                producto.nombre.lowercase().startsWith(
                                    textoBusqueda.lowercase()
                                )
                            }) { producto ->
                                Text(
                                    producto.nombre,
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            carritoCompra.swapList(
                                                carritoCompra + listOf(
                                                    ProductoComprado(producto, producto.precio, 1)
                                                )
                                            )
                                            textoBusqueda = ""
                                        }
                                )
                            }
                        }
                    }
                }
            }

            item{ Spacer(modifier = Modifier.padding(10.dp))}

            itemsIndexed(carritoCompra) { indice, productoComprado ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(
                            if (indice % 2 == 0) MaterialTheme.colors.secondaryVariant.copy(
                                0.5f
                            ) else MaterialTheme.colors.secondary.copy(0.5f)
                        )
                        .padding(start = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text("${productoComprado.producto.nombre} (${productoComprado.cantidadTotal})")
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ){
                        IconButton(
                            onClick = {
                                carritoCompra.swapList(carritoCompra.also { it.find { pc -> pc == productoComprado }.apply { this!!.cantidadTotal += 1 } }.toList())
                            }
                        ) {
                            Icon(Icons.Default.Add, "Sumar")
                        }
                        IconButton(
                            onClick = {
                                if (productoComprado.cantidadTotal > 1)
                                    carritoCompra.swapList(carritoCompra.also { it.find { pc -> pc == productoComprado }.apply { this!!.cantidadTotal -= 1 } }.toList())
                                else
                                    carritoCompra.swapList(carritoCompra.toMutableList().also { it.remove(productoComprado) })
                            }
                        ) {
                            Icon(Icons.Default.Remove, "Restar")
                        }
                        IconButton(
                            onClick = {
                                carritoCompra.swapList(carritoCompra.toMutableList().also { it.remove(productoComprado) })
                            }
                        ) {
                            Icon(Icons.Default.Close, "Eliminar")
                        }
                    }
                }
            }
            item {Spacer(modifier = Modifier.padding(40.dp))}
        }
    }
}

fun <T> SnapshotStateList<T>.swapList(list: List<T>){
    clear()
    addAll(list)
}

