package com.warh.restaurante.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.warh.restaurante.R
import com.warh.restaurante.model.Empleado
import com.warh.restaurante.model.Producto
import com.warh.restaurante.ui.components.*
import com.warh.restaurante.utils.Screens

@Composable
fun ManagerScreen(
    navController: NavController
) {
    val context = LocalContext.current

    //TODO Cambiar datasource de los datos
    val lista1 = listOf(
        Empleado(0, "EMPLEADO 1", "00.000.000", "+5493496000000", "CALLE A NUMERO 0000"),
        Empleado(1, "EMPLEADO 2", "11.111.111", "+5493496111111", "CALLE B NUMERO 1111")
    )

    val lista2 = listOf(
        Empleado(0, "DELIVERY 1", "99.999.999", "+5493496999999", "CALLE Z NUMERO 9999"),
        Empleado(1, "DELIVERY 2", "88.888.888", "+5493496888888", "CALLE Y NUMERO 8888")
    )

    val lista0 = listOf(
        Producto(0, "PRODUCTO 1", "100", "https://picsum.photos/110", "CAT A"),
        Producto(1, "PRODUCTO 2", "200", "https://picsum.photos/120", "CAT B"),
    )

    var seleccionado by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CustomTopBar(
                titulo = context.getString(R.string.TITULO_ADMINISTRACION),
                accionBotonIzquierda = { navController.popBackStack() },
                accionBotonDerecha = { /*TODO accion click usuario topbar*/ },
                usuarioLogueado = true,
                permiteVolver = true,
                usuarioImagenLink = "https://picsum.photos/100" //TODO cambiar por foto perfil usuario
            )
        },
        bottomBar = {
            CustomBottomBar(seleccionado){
                seleccionado = it
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when(seleccionado){
                        0 -> navController.navigate(Screens.AddProductScreen.route)
                        1 -> navController.navigate(Screens.AddEmployeeScreen.route)
                        2 -> navController.navigate(Screens.AddDeliveryScreen.route)
                        else -> throw ClassNotFoundException("Opción $seleccionado de BottomBar no existe")
                    }
                },
                backgroundColor = MaterialTheme.colors.secondary) {
                Icon(Icons.Default.Add, "Agregar", tint = Color.Black)
            }
        },
        isFloatingActionButtonDocked = false,
        floatingActionButtonPosition = FabPosition.End,
        backgroundColor = MaterialTheme.colors.primary.copy(0.1f)
    ) { scaffoldPadding ->
        LazyColumn(
            Modifier
                .padding(scaffoldPadding)
                .fillMaxWidth()
        ){
            items(
                when(seleccionado){
                    0 -> lista0
                    1 -> lista1
                    2 -> lista2
                    else -> throw ClassNotFoundException("Opción $seleccionado de BottomBar no existe")
                }
            ) { item ->
                when (item){
                    //TODO agregar acciones click en card view
                    is Empleado -> EmployeeCardView(item){}
                    is Producto -> ManagerProductCardView(item){}
                }
            }
        }
    }
}