package com.warh.restaurante.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseUser
import com.warh.restaurante.R
import com.warh.restaurante.utils.Screens
import com.warh.restaurante.viewmodel.UserViewModel

@Composable
fun CustomDrawer(usuario: FirebaseUser?, viewModel: UserViewModel, navController: NavController) {
    val context = LocalContext.current

    var tipoUsuario by remember { mutableStateOf("") }

    usuario?.let {
        viewModel.rangoCuenta(usuario.email!!){ rango: String ->
            tipoUsuario = rango
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary.copy(0.1f)),
    ) {

        Column(
            Modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.padding(10.dp))

            Image(
                painter = usuario?.let {
                    rememberAsyncImagePainter(
                        "https://picsum.photos/100",
                        placeholder = painterResource(
                            R.drawable.placeholder
                        )
                    )
                } ?: painterResource(id = R.drawable.placeholder),
                contentDescription = "Imagen de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape)
            )

            usuario?.let {
                Text(usuario.displayName!!, style = MaterialTheme.typography.caption)
            } ?: Text(
                context.getString(R.string.TEXTO_INICIAR_SESION),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.clickable {
                    navController.navigate(
                        Screens.LoginScreen.route
                    )
                })

            Spacer(Modifier.padding(10.dp))
        }

        Divider(color = MaterialTheme.colors.primary.copy(0.5f))

        Column(
            Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            usuario?.let {
                when (tipoUsuario) {
                    "NORMAL" -> {
                        Text(
                            context.getString(Screens.NewOrderScreen.title),
                            modifier = Modifier
                                .clickable { navController.navigate(Screens.NewOrderScreen.route) }
                                .fillMaxWidth()
                        )
                        Text(
                            context.getString(Screens.MyOrderListScreen.title),
                            modifier = Modifier
                                .clickable { navController.navigate(Screens.MyOrderListScreen.route) }
                                .fillMaxWidth()
                        )
                        Text(
                            context.getString(Screens.MyAccountScreen.title),
                            modifier = Modifier
                                .clickable { navController.navigate(Screens.MyAccountScreen.route) }
                                .fillMaxWidth()
                        )
                        Text(
                            context.getString(R.string.BOTON_CERRAR_SESION),
                            modifier = Modifier
                                .clickable { /*TODO Cerrar sesión*/ }
                                .fillMaxWidth()
                        )
                    }
                    "MANAGER" -> {
                        Text(
                            context.getString(Screens.ManagerScreen.title),
                            modifier = Modifier
                                .clickable { navController.navigate(Screens.ManagerScreen.route) }
                                .fillMaxWidth()
                        )
                    }
                    "EMPLEADO" -> {

                    }
                    "DELIVERY" -> {

                    }
                }
            }
        }
    }
}