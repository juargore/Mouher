package com.ocean.domain.entities

data class ResponseData(
    val Error: Int = 0,
    val Mensaje: String? = null
){
    fun toResponseUI() = ResponseUI(
        hasErrors = Error > 0,
        message = Mensaje
    )
}

data class ResponseUI(
    val hasErrors: Boolean = false,
    val message: String? = null
)

data class ResponsePaymentStatus(
    val Error: Int = 0,
    val Mensaje: String = "",
    val StatusPago1: Int
)