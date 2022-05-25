package com.warh.restaurante.utils.exceptions

class FirebaseDatabaseRequestCancelledException(funcion: String) : Exception("Petición de FirebaseDatabase: $funcion cancelada.")