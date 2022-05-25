package com.warh.restaurante.dao

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.warh.restaurante.utils.exceptions.FirebaseDatabaseRequestCancelledException
import com.warh.restaurante.utils.exceptions.UserDoesntExistsException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

interface UserDao {
    suspend fun registrarCuenta(
        correo: String,
        contrasena: String,
        nombreCompleto: String,
        direccion: String,
        telefono: String,
        scope: CoroutineScope
    ): AuthResult?

    suspend fun iniciarSesionCuenta(
        correo: String,
        contrasena: String
    ): AuthResult?

    fun rangoCuenta(
        correo: String,
        callback: (String) -> Unit
    )
}

class UserDaoImpl : UserDao {
    companion object {
        val database = FirebaseDatabase.getInstance().reference
        val auth = FirebaseAuth.getInstance()
        const val USERS_PATH = "usuarios"
    }

    override suspend fun registrarCuenta(
        correo: String,
        contrasena: String,
        nombreCompleto: String,
        direccion: String,
        telefono: String,
        scope: CoroutineScope,
    ): AuthResult? {
        try {
            val user = scope.async {
                val user = auth.createUserWithEmailAndPassword(correo, contrasena).await()
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(nombreCompleto)
                    .build()
                user.user!!.updateProfile(profileUpdates)

                user
            }

            scope.launch {
                val datosUsuario = mutableMapOf<String, Any>()
                datosUsuario["email"] = correo
                datosUsuario["nombre"] = nombreCompleto
                datosUsuario["direccion"] = direccion
                datosUsuario["telefono"] = telefono
                datosUsuario["rango"] = "NORMAL"
                datosUsuario["reputacion"] = "0"

                val correoV2 = correo.replace(".", ",").replace("@", " ")

                database.child(USERS_PATH).child(correoV2).setValue(datosUsuario)
            }

            return user.await()

        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun iniciarSesionCuenta(
        correo: String,
        contrasena: String
    ): AuthResult? {
        return try {
            auth.signInWithEmailAndPassword(correo, contrasena).await()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun rangoCuenta(correo: String, callback: (String) -> Unit) {
        val correoV2 = correo.replace(".", ",").replace("@", " ")
        database.child(USERS_PATH).child(correoV2).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    callback.invoke(snapshot.child("rango").value.toString())
                } else {
                    throw UserDoesntExistsException()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw FirebaseDatabaseRequestCancelledException("rangoCuenta")
            }
        })
    }
}