package com.warh.restaurante.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.warh.restaurante.R
import com.warh.restaurante.ui.components.CustomTextField
import com.warh.restaurante.ui.components.CustomTopBar

@Composable
fun RegisterScreen(
    navController: NavController
) {
    val context = LocalContext.current

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var contrasenaVisible by remember { mutableStateOf(false) }
    var nombreCompleto by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    Scaffold(
        backgroundColor = MaterialTheme.colors.primary.copy(0.1f),
        topBar = {
            CustomTopBar(
                titulo = context.getString(R.string.TITULO_REGISTRARSE),
                accionBotonIzquierda = { navController.popBackStack() },
                usuarioImagenLink = "",
                permiteVolver = true,
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
                tipoTeclado = KeyboardType.Email
            )

            CustomTextField(
                texto = contrasena,
                placeholder = context.getString(R.string.PLACEHOLDER_CONTRASENA),
                accionCambioDeValor = { contrasena = it },
                iconoTrasero = {
                    IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                        Icon(Icons.Default.Visibility, "Contraseña visible")
                    }
                },
                passwordVisible = contrasenaVisible,
                tipoTeclado = KeyboardType.Password
            )

            CustomTextField(
                texto = nombreCompleto,
                placeholder = context.getString(R.string.PLACEHOLDER_NOMBRE_COMPLETO),
                accionCambioDeValor = { nombreCompleto = it }
            )

            CustomTextField(
                texto = direccion,
                placeholder = context.getString(R.string.PLACEHOLDER_DIRECCION),
                accionCambioDeValor = { direccion = it }
            )

            CustomTextField(
                texto = telefono,
                placeholder = context.getString(R.string.PLACEHOLDER_TELEFONO),
                accionCambioDeValor = { telefono = it },
                tipoTeclado = KeyboardType.Phone
            )

            Spacer(Modifier.padding(5.dp))

            Button(
                onClick = { /*TODO Registrar cuenta*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    context.getString(R.string.BOTON_REGISTRARSE),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}