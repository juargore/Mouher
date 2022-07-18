package com.glass.domain.entities

import java.io.Serializable

data class ParcelsResponse(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val BaseCalculo: String? = null,
    val Alto: Int? = null,
    val Ancho: Int? = null,
    val Largo: Int? = null,
    val Peso: Double? = null,
    val Fragilidad: Int? = null,
    val Opciones: List<ParcelData>? = null
)

data class ParcelData(
    val LinkImagen: String? = null,
    val Paqueteria: String? = null,
    val Servicio: String? = null,
    val Descripcion: String? = null,
    val Estimacion: String? = null,
    val Importe: Double? = 0.0,
    var Seleccionado: Boolean? = null,
    var Alto: Int? = null,
    var Ancho: Int? = null,
    var Largo: Int? = null,
    var Peso: Double? = null,
    var Fragilidad: Int? = null,
) : Serializable