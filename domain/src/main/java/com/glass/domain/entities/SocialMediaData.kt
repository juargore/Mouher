package com.glass.domain.entities

data class SocialMediaData(
    val Id: String? = null,
    val Nombre: String? = null,
    val DescripcionLarga: String? = null,
    var Fotografia2: String? = null,
    var Fotografia3: String? = null
) {

    fun getSocialMediaUI(): SocialMediaUI{

        return SocialMediaUI(
            id = Id,
            name = Nombre,
            linkToOpen = DescripcionLarga,
            urlImage = Fotografia2
        )
    }
}