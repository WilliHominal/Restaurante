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
fun AddProductScreen(
    navController: NavController
) {
    val context = LocalContext.current

    var categoriaProducto by remember { mutableStateOf("") }
    var nombreProducto by remember { mutableStateOf("") }
    var precioProducto by remember { mutableStateOf("") }
    var linkImagenProducto by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomTopBar(
                titulo = context.getString(R.string.TITULO_NUEVO_PRODUCTO),
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
                texto = categoriaProducto,
                placeholder = context.getString(R.string.PLACEHOLDER_CATEGORIA),
                accionCambioDeValor = { categoriaProducto = it }
            )
            CustomTextField(
                texto = nombreProducto,
                placeholder = context.getString(R.string.PLACEHOLDER_NOMBRE_PRODUCTO),
                accionCambioDeValor = { nombreProducto = it }
            )
            CustomTextField(
                texto = precioProducto,
                placeholder = context.getString(R.string.PLACEHOLDER_PRECIO),
                accionCambioDeValor = { precioProducto = it },
                tipoTeclado = KeyboardType.Decimal
            )
            CustomTextField(
                texto = linkImagenProducto,
                placeholder = context.getString(R.string.PLACEHOLDER_LINK_IMAGEN_PRODUCTO),
                accionCambioDeValor = { linkImagenProducto = it },
                tipoTeclado = KeyboardType.Uri
            )

            Spacer(Modifier.padding(5.dp))

            Button(
                onClick = { /*TODO acción Guardar Producto (con verificacion de datos validos)*/ },
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