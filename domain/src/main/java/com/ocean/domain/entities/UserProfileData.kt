package com.ocean.domain.entities

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
    val Genero: Int? = null,
    val Contrasena: String? = null,
    var DomicilioEnvio: List<UserAddressData>? = null,
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
    val Pais: Int? = null,
    val Estado: Int? = null,
    val Municipio: String? = null
)