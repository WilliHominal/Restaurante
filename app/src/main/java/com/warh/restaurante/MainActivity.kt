package com.warh.restaurante

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.warh.restaurante.ui.screens.*
import com.warh.restaurante.ui.theme.RestauranteTheme
import com.warh.restaurante.utils.Screens
import com.warh.restaurante.viewmodel.ProductViewModel
import com.warh.restaurante.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userViewModel by viewModels<UserViewModel>()
            val productViewModel by viewModels<ProductViewModel>()

            RestauranteTheme { NavigationHost(userViewModel, productViewModel) }
        }
    }
}

@Composable
fun NavigationHost(userViewModel: UserViewModel, productViewModel: ProductViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route){
        composable(Screens.HomeScreen.route){
            HomeScreen(navController, userViewModel = userViewModel, productViewModel = productViewModel)
        }
        composable(Screens.LoginScreen.route){
            LoginScreen(navController, userViewModel = userViewModel)
        }
        composable(Screens.RegisterScreen.route){
            RegisterScreen(navController, userViewModel = userViewModel)
        }
        composable(Screens.NewOrderScreen.route){
            NewOrderScreen(navController, productViewModel = productViewModel)
        }
        composable(Screens.MyOrderListScreen.route){
            MyOrderListScreen(navController)
        }
        composable(Screens.MyAccountScreen.route){
            MyAccountScreen(navController)
        }
        composable(Screens.ManagerScreen.route){
            ManagerScreen(navController, productViewModel = productViewModel)
        }
        composable(Screens.AddProductScreen.route){
            AddProductScreen(navController, productViewModel = productViewModel)
        }
        composable(Screens.AddEmployeeScreen.route){
            AddEmployeeScreen(navController)
        }
        composable(Screens.AddDeliveryScreen.route){
            AddDeliveryScreen(navController)
        }
    }
}