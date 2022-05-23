package com.warh.restaurante.utils

import androidx.annotation.StringRes
import com.warh.restaurante.R

sealed class ErrorCodes(@StringRes val title: Int) {
    object CorreoNoValidoError: ErrorCodes(R.string.MENSAJE_ERROR_CORREO_NO_VALIDO)
    object ContrasenaCharNoValidoError: ErrorCodes(R.string.MENSAJE_ERROR_CONTRASENA_NO_VALIDA)
    object ContrasenaCortaError: ErrorCodes( R.string.MENSAJE_ERROR_CONTRASENA_CORTA)
    object NombreCharNoValidoError: ErrorCodes(R.string.MENSAJE_ERROR_NOMBRE_NO_VALIDO)
    object DireccionCharNoValidoError: ErrorCodes(R.string.MENSAJE_ERROR_DIRECCION_NO_VALIDO)
    object TelefonoCharNoValidoError: ErrorCodes(R.string.MENSAJE_ERROR_TELEFONO_NO_VALIDO)

    object CamposIncompletosError: ErrorCodes(R.string.MENSAJE_ERROR_CAMPOS_INCOMPLETOS)

    object RegistroExitoso: ErrorCodes(R.string.MENSAJE_REGISTRO_EXITOSO)
    object InicioSesionExitoso: ErrorCodes(R.string.MENSAJE_INICIO_SESION_EXITOSO)
}