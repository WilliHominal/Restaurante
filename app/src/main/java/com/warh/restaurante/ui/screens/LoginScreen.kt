package com.warh.restaurante.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.warh.restaurante.R
import com.warh.restaurante.ui.components.CustomTextField
import com.warh.restaurante.ui.components.CustomTopBar
import com.warh.restaurante.utils.Screens

@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var contrasenaVisible by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.primary.copy(0.1f),
        topBar = {
            CustomTopBar(
                titulo = context.getString(R.string.TITULO_LOGIN),
                permiteVolver = true,
                accionBotonIzquierda = { navController.popBackStack() },
                usuarioImagenLink = "",
            )
        }
    ) { scaffoldPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = 25.dp)
                .fillMaxSize()
        ) {
            Spacer(Modifier.padding(bottom = 15.dp))

            CustomTextField(
                texto = correo,
                placeholder = context.getString(R.string.PLACEHOLDER_CORREO_ELECTRONICO),
                accionCambioDeValor = { correo = it },
            )

            CustomTextField(
                texto = contrasena,
                placeholder = context.getString(R.string.PLACEHOLDER_CONTRASENA),
                passwordVisible = contrasenaVisible,
                iconoTrasero = {
                    IconButton(
                        onClick = {
                            contrasenaVisible = !contrasenaVisible
                        }
                    ) {
                        Icon(Icons.Default.Visibility, "Visibilidad contraseña")
                    }
                },
                accionCambioDeValor = { contrasena = it },
            )

            Text(
                text = context.getString(R.string.TEXTO_SIN_CUENTA_REGISTRATE),
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .clickable {
                        navController.navigate(Screens.RegisterScreen.route)
                    }
            )

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            Button(
                onClick = { /*TODO Iniciar sesión*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    context.getString(R.string.BOTON_INICIAR_SESION),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}