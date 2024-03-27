package com.ocean.domain.entities

data class SocialMediaDataResponse(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Redes: List<SocialMediaData>? = null
)

data class SocialMediaData(
    val IdRed: Int? = null,
    val Nombre: String? = null,
    val Link: String? = null,
    val Logotipo1: String? = null,
    val Logotipo2: String? = null
){
    fun getSocialMediaUI() = SocialMediaUI(
        id = IdRed,
        name = Nombre,
        linkToOpen = Link,
        urlImage = Logotipo1
    )
}

data class SocialMediaUI(
    val id: Int? = null,
    val name: String? = null,
    var urlImage: String? = null,
    val linkToOpen: String? = null
)