package com.warh.restaurante

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.warh.restaurante.ui.screens.*
import com.warh.restaurante.ui.theme.RestauranteTheme
import com.warh.restaurante.utils.Screens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestauranteTheme { NavigationHost() }
        }
    }
}

@Composable
fun NavigationHost(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route){
        composable(Screens.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(Screens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(Screens.RegisterScreen.route){
            RegisterScreen(navController)
        }
        composable(Screens.NewOrderScreen.route){
            NewOrderScreen(navController)
        }
        composable(Screens.MyOrderListScreen.route){
            MyOrderListScreen(navController)
        }
        composable(Screens.MyAccountScreen.route){
            MyAccountScreen(navController)
        }
        composable(Screens.ManagerScreen.route){
            ManagerScreen(navController)
        }
        composable(Screens.AddProductScreen.route){
            AddProductScreen(navController)
        }
        composable(Screens.AddEmployeeScreen.route){
            AddEmployeeScreen(navController)
        }
        composable(Screens.AddDeliveryScreen.route){
            AddDeliveryScreen(navController)
        }
    }
}