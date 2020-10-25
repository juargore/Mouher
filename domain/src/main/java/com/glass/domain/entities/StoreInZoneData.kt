package com.glass.domain.entities

data class StoreInZoneData(
    val Id: Int? = null,
    val Status: Int? = null,
    val Codigo: String? = null,
    val Nombre: String? = null,
    val CodigoZona: String? = null,
    val NombreZona: String? = null,
    val Fotografia1: String? = null,
    val Fotografia2: String? = null,
    val Fotografia3: String? = null,
    val Fotografia4: String? = null,
    val Fotografia5: String? = null
){

    fun getStoreInZoneUI(): StoreInZoneUI{
        return StoreInZoneUI(
            id = Id.toString(),
            urlImage = Fotografia1,
            name = Nombre,
            totalProducts = 0
        )
    }

}