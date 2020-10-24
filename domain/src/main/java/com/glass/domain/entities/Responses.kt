package com.glass.domain.entities

data class ResponseMall(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<MallData>?
)