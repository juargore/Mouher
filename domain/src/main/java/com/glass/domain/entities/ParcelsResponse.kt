package com.glass.domain.entities

data class ParcelsResponse(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val BaseCalculo: String? = null,
    val Opciones: List<ParcelData>? = null
)

data class ParcelData(
    val Paquetería: String? = null,
    val Servicio: String? = null,
    val Descripcion: String? = null,
    val Estimacion: String? = null,
    val Importe: Float? = 0.0f
)