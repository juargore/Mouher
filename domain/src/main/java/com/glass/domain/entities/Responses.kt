package com.glass.domain.entities

data class ResponseSocialMedia(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<SocialMediaData>?
)

data class ResponseNewArrivals(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<NewArrivalProductData>?
)

data class ResponseFullProduct(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<ProductData>?
)