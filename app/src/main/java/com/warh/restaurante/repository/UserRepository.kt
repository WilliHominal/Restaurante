package com.warh.restaurante.repository

import com.warh.restaurante.dao.UserDao
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun registrarCuenta(correo: String, contrasena: String, nombreCompleto: String, direccion: String, telefono: String, scope: CoroutineScope) = userDao.registrarCuenta(correo, contrasena, nombreCompleto, direccion, telefono, scope)

    suspend fun iniciarSesionCuenta(correo: String, contrasena: String) = userDao.iniciarSesionCuenta(correo, contrasena)
}