package com.ocean.domain.entities

data class SponsorData(
    val IdTienda: Int? = null, // ID of internal store to open
    val Nombre: String? = null, // Sponsor name
    val Logotipo: String? = null, // Url image
    val Link: String? = null, // URL link in case it exists
){

    fun getSponsorStoreUI() = SponsorUI(
        id = IdTienda,
        urlImage = Logotipo,
        name = Nombre,
        idOfStoreToOpen = IdTienda
    )

}

data class SponsorUI(
    val id: Int? = null,
    val name: String? = null,
    var urlImage: String? = null,
    var willOpenExternalUrl: Boolean = false,
    var linkToOpen: String? = null,
    var idOfStoreToOpen: Int? = null,
)