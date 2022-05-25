package com.warh.restaurante.ui.screens

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.warh.restaurante.R
import com.warh.restaurante.ui.components.CustomTextField
import com.warh.restaurante.ui.components.CustomTopBar
import com.warh.restaurante.utils.ErrorCodes
import com.warh.restaurante.utils.Screens
import com.warh.restaurante.viewmodel.UserViewModel
import kotlinx.coroutines.*

@Composable
fun LoginScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var contrasenaVisible by remember { mutableStateOf(false) }

    var notificacionExtendida by remember { mutableStateOf(false) }
    var textoErrorMensaje by remember { mutableStateOf("") }

    var botonInicarSesionHabilitado by remember { mutableStateOf(true) }

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
                onClick = {
                    userViewModel.viewModelScope.launch {
                        botonInicarSesionHabilitado = false

                        val verificacion = validarDatos(correo, contrasena)

                        val corrutinaRegistro =
                            if (verificacion == ErrorCodes.InicioSesionExitoso)
                                async { userViewModel.iniciarSesion(correo, contrasena) }
                            else
                                null

                        if (verificacion != ErrorCodes.InicioSesionExitoso){
                            withContext(Dispatchers.Default) {
                                mostrarAviso(context.getString(verificacion.title))
                            }
                        } else {
                            val authResult = corrutinaRegistro?.await()

                            authResult?.let {
                                withContext(Dispatchers.Default) { mostrarAviso(context.getString(verificacion.title)) }
                                navController.navigate(Screens.HomeScreen.route){
                                    popUpTo(0)
                                }
                            } ?: mostrarAviso(context.getString(R.string.MENSAJE_ERROR_DATOS_INCORRECTOS))
                        }

                        botonInicarSesionHabilitado = true
                    }
                },
                enabled = botonInicarSesionHabilitado,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    context.getString(R.string.BOTON_INICIAR_SESION),
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

private fun validarDatos(correo: String, contrasena: String) : ErrorCodes {
    if (correo.isBlank() || contrasena.isBlank()){
        return ErrorCodes.CamposIncompletosError
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
        return ErrorCodes.CorreoNoValidoError
    }
    if (!contrasena.matches("[\\w-]+".toRegex())){
        return ErrorCodes.ContrasenaCharNoValidoError
    }
    return ErrorCodes.InicioSesionExitoso
}