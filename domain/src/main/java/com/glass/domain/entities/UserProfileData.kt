package com.glass.domain.entities

data class UserProfileData(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Id: Int? = null,
    val Status: Int? = null,
    val Codigo: String? = null,
    val Nombre: String? = null,
    val ApellidoP: String? = null,
    val ApellidoM: String? = null,
    val Correo: String? = null,
    val TelMovil: String? = null,
    val FechaNac: String? = null,
    val Genero: Int? = 1, // TODO: Update with server info
    val Contrasena: String? = null,
    val DomicilioEnvio: List<UserAddressData>? = null,
    val DomicilioFiscal: List<UserAddressData>? = null
)

data class UserAddressData(
    val IdDomicilio: Int? = null,
    val Calle: String? = null,
    val NumInt: String? = null,
    val NumExt: String? = null,
    val Cruzamientos: String? = null,
    val Colonia: String? = null,
    val CP: String? = null,
    val Pais: String? = null,
    val IdPais: Int? = 117, // TODO: Update with server info
    val Estado: String? = null,
    val IdEstado: Int? = 14, // TODO: Update with server info
    val Municipio: String? = null
)