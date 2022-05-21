package com.warh.restaurante.utils

import androidx.annotation.StringRes
import com.warh.restaurante.R

sealed class Screens(val route: String, @StringRes val title: Int) {
    object LoginScreen: Screens("LOGIN_SCREEN", R.string.TITULO_LOGIN)
    object RegisterScreen: Screens("REGISTER_SCREEN", R.string.TITULO_REGISTRARSE)
    object HomeScreen: Screens("HOME_SCREEN", R.string.TITULO_INICIO)
    object NewOrderScreen: Screens("NEW_ORDER_SCREEN", R.string.TITULO_NUEVO_PEDIDO)
    object MyOrderListScreen: Screens("MY_ORDER_LIST_SCREEN", R.string.TITULO_MIS_PEDIDOS)
    object MyAccountScreen: Screens("MY_ACCOUNT_SCREEN", R.string.TITULO_MI_CUENTA)
    object ManagerScreen: Screens("MANAGER_SCREEN", R.string.TITULO_ADMINISTRACION)
    object AddProductScreen: Screens("ADD_PRODUCT_SCREEN", R.string.TITULO_NUEVO_PRODUCTO)
    object AddEmployeeScreen: Screens("ADD_EMPLOYEE_SCREEN", R.string.TITULO_NUEVO_EMPLEADO)
    object AddDeliveryScreen: Screens("ADD_DELIVERY_SCREEN", R.string.TITULO_NUEVO_DELIVERY)
}