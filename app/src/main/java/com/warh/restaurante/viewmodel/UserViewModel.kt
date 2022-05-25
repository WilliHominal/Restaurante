package com.warh.restaurante.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warh.restaurante.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    suspend fun registrarUsuario(
        correo: String,
        contrasena: String,
        nombreCompleto: String,
        direccion: String,
        telefono: String,
    ) = userRepository.registrarCuenta(correo, contrasena, nombreCompleto, direccion, telefono, viewModelScope)

    suspend fun iniciarSesion(
        correo: String,
        contrasena: String
    ) = userRepository.iniciarSesionCuenta(correo, contrasena)

    fun rangoCuenta(
        correo: String,
        callback: (String) -> Unit
    ) = userRepository.rangoCuenta(correo, callback)
}