package com.ocean.domain.entities

data class RegistrationData(
    val Error: Int = 0,
    val Mensaje: String? = null,
    val Id: Int? = null

// Also exists on this api response:
// FechaAlta (2021-09-02)
// FechaUltimaModif (0000-00-00)
)

data class LoginData(
    val Error: Int = 0,
    val Mensaje: String? = null,
    val IdCliente: Int? = null,
    val Nombre: String? = null,
    val ApellidoP: String? = null,
    val ApellidoM: String? = null,
    val FechaAlta: String? = null
)