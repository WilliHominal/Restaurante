package com.warh.restaurante.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
fun MyAccountScreen(
    navController: NavController
) {
    val context = LocalContext.current

    //TODO cambiar datasource datos iniciales
    val correo = "MICORREO@GMAIL.COM"
    var nombreCompleto by remember { mutableStateOf("NOMBRE COMPLETO") }
    var direccion by remember { mutableStateOf("XYZ 1234") }
    var telefono by remember { mutableStateOf("+5493496000000") }

    Scaffold(
        backgroundColor = MaterialTheme.colors.primary.copy(0.1f),
        topBar = {
            CustomTopBar(
                titulo = context.getString(R.string.TITULO_MI_CUENTA),
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
                accionCambioDeValor = {},
                habilitado = false
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
                    context.getString(R.string.BOTON_GUARDAR),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}