package com.warh.restaurante.ui.screens

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.warh.restaurante.R
import com.warh.restaurante.ui.components.CustomTextField
import com.warh.restaurante.ui.components.CustomTopBar
import com.warh.restaurante.utils.ErrorCodes
import com.warh.restaurante.viewmodel.UserViewModel
import kotlinx.coroutines.*

@Composable
fun RegisterScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var contrasenaVisible by remember { mutableStateOf(false) }
    var nombreCompleto by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    var botonRegistrarseHabilitado by remember { mutableStateOf(true) }

    var notificacionExtendida by remember { mutableStateOf(false) }
    var textoErrorMensaje by remember { mutableStateOf("") }

    suspend fun mostrarAviso(errorMensaje: String) {
        if (!notificacionExtendida) {
            textoErrorMensaje = errorMensaje
            notificacionExtendida = true
            delay(1000L)
            notificacionExtendida = false
            textoErrorMensaje = ""
        }
    }

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
                onClick = {
                    userViewModel.viewModelScope.launch {

                        botonRegistrarseHabilitado = false

                        val verificacion = validarDatos(correo, contrasena, nombreCompleto, direccion, telefono)

                        val corrutinaRegistro =
                            if (verificacion == ErrorCodes.RegistroExitoso)
                                async { userViewModel.registrarUsuario(correo, contrasena, nombreCompleto, direccion, telefono) }
                            else
                                null

                        if (verificacion != ErrorCodes.RegistroExitoso){
                            withContext(Dispatchers.Default) {
                                mostrarAviso(context.getString(verificacion.title))
                            }
                        } else {
                            val authResult = corrutinaRegistro?.await()

                            authResult?.let {
                                withContext(Dispatchers.Default) { mostrarAviso(context.getString(verificacion.title)) }
                                navController.popBackStack()
                            } ?: mostrarAviso(context.getString(R.string.MENSAJE_ERROR_CORREO_EN_USO))
                        }

                        botonRegistrarseHabilitado = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = botonRegistrarseHabilitado
            ) {
                Text(
                    context.getString(R.string.BOTON_REGISTRARSE),
                    style = MaterialTheme.typography.button
                )
            }
        }
        AnimatedVisibility(
            visible = notificacionExtendida,
            enter = slideInVertically(
                initialOffsetY = { altura -> -altura },
                animationSpec = tween(150, easing = LinearOutSlowInEasing)
            ),
            exit = slideOutVertically(
                targetOffsetY = { altura -> -altura },
                animationSpec = tween(100, easing = FastOutLinearInEasing)
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.DarkGray
            ) {
                Text(
                    textoErrorMensaje,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

private fun validarDatos(correo: String, contrasena: String, nombre: String, direccion: String, telefono: String) : ErrorCodes {
    if (correo.isBlank() || contrasena.isBlank() || nombre.isBlank() || direccion.isBlank() || telefono.isBlank()){
        return ErrorCodes.CamposIncompletosError
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
        return ErrorCodes.CorreoNoValidoError
    }
    if (!contrasena.matches("[\\w-]+".toRegex())){
        return ErrorCodes.ContrasenaCharNoValidoError
    }
    if (contrasena.length < 6){
        return ErrorCodes.ContrasenaCortaError
    }
    if (!nombre.matches("[a-zA-ZñÑ]+ [a-zA-ZñÑ]+".toRegex())){
        return ErrorCodes.NombreCharNoValidoError
    }
    if (!direccion.matches("([\\w]+ )+[0-9]+".toRegex())){
        return ErrorCodes.DireccionCharNoValidoError
    }
    if (!telefono.matches("[+]?[0-9]+".toRegex())){
        return ErrorCodes.TelefonoCharNoValidoError
    }

    return ErrorCodes.RegistroExitoso
}