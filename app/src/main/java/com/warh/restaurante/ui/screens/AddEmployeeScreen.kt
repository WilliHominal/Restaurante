package com.warh.restaurante.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.warh.restaurante.R
import com.warh.restaurante.ui.components.CustomTextField
import com.warh.restaurante.ui.components.CustomTopBar

@Composable
fun AddEmployeeScreen(
    navController: NavController
) {
    val context = LocalContext.current

    var nombreCompleto by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomTopBar(
                titulo = context.getString(R.string.TITULO_NUEVO_EMPLEADO),
                accionBotonIzquierda = { navController.popBackStack() },
                usuarioLogueado = true,
                usuarioImagenLink = "https://picsum.photos/100", //TODO cambiar por foto perfil usuario
                permiteVolver = true,
                accionBotonDerecha = { /*TODO accion click usuario topbar*/ }
            )
        },
        backgroundColor = MaterialTheme.colors.primary.copy(0.1f)
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = 25.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(Modifier.padding(bottom = 15.dp))
            CustomTextField(
                texto = nombreCompleto,
                placeholder = context.getString(R.string.PLACEHOLDER_NOMBRE_COMPLETO),
                accionCambioDeValor = { nombreCompleto = it }
            )
            CustomTextField(
                texto = dni,
                placeholder = context.getString(R.string.PLACEHOLDER_DNI),
                accionCambioDeValor = { dni = it },
                tipoTeclado = KeyboardType.Decimal
            )
            CustomTextField(
                texto = direccion,
                placeholder = context.getString(R.string.PLACEHOLDER_DIRECCION),
                accionCambioDeValor = { direccion = it },
            )
            CustomTextField(
                texto = telefono,
                placeholder = context.getString(R.string.PLACEHOLDER_TELEFONO),
                accionCambioDeValor = { telefono = it },
                tipoTeclado = KeyboardType.Phone
            )

            Spacer(Modifier.padding(5.dp))

            Button(
                onClick = { /*TODO acción Guardar Empleado (con verificacion de datos validos)*/ },
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