package com.warh.restaurante.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.warh.restaurante.R

sealed class ManagerBottomBarScreen(
    val posicion: Int,
    @StringRes val titulo: Int,
    val icono: ImageVector
){
    object Productos: ManagerBottomBarScreen(0, R.string.BOTTOMBAR_TITULO_PRODUCTOS, Icons.Default.Restaurant)
    object Empleados: ManagerBottomBarScreen(1, R.string.BOTTOMBAR_TITULO_EMPLEADOS, Icons.Default.Groups)
    object Delivery: ManagerBottomBarScreen(2, R.string.BOTTOMBAR_TITULO_DELIVERY, Icons.Default.DeliveryDining)
}

@Composable
fun CustomBottomBar(
    seleccionado: Int,
    accionCambio: (Int) -> Unit
) {
    val context = LocalContext.current

    val lista = listOf(
        ManagerBottomBarScreen.Productos,
        ManagerBottomBarScreen.Empleados,
        ManagerBottomBarScreen.Delivery
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = Color.White
    ) {
        lista.forEach {
            BottomNavigationItem(
                icon = { Icon(it.icono, context.getString(it.titulo)) },
                label = { Text(context.getString(it.titulo), fontSize = 15.sp, color = Color.White) },
                selected = seleccionado == it.posicion,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black,
                alwaysShowLabel = false,
                onClick = { accionCambio(it.posicion) },
            )
        }
    }
}