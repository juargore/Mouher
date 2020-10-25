package com.glass.domain.entities

data class ZoneData(
    val Id: Int? = null,
    val Status: Int? = null,
    val Codigo: String? = null,
    val Nombre: String? = null,
    val DescripcionCorta: String? = null,
    val Fotografia1: String? = null,
    val IdPlaza: Int? = null,
    val CodigoPlaza: String? = null,
    val NombrePlaza: String? = null
){

    fun toZoneUI(): ZoneUI{
        return ZoneUI(
            id = Id,
            name = Nombre,
            urlImage = Fotografia1,
            description = DescripcionCorta
        )
    }
}