package com.glass.domain.entities

data class SponsorStoreData(
    val Id: Int? = null,
    val Status: Int? = null,
    val IdTienda: Int? = null,
    val NombreTienda: String? = null
){

    fun getSponsorStoreUI(): SponsorStoreUI{
        return SponsorStoreUI(
            id = Id.toString(),
            urlImage = "",
            name = NombreTienda
        )
    }
}