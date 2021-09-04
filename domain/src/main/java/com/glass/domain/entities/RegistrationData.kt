package com.glass.domain.entities

data class RegistrationData(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Id: Int? = null

// Also exists:
// FechaAlta (2021-09-02)
// FechaUltimaModif (0000-00-00)
)

data class LoginData(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val IdCliente: Int? = null,
    val Nombre: String? = null,
    val ApellidoP: String? = null,
    val ApellidoM: String? = null
)