package com.glass.domain.entities

data class ZoneData(
    val IdZona: Int? = null,
    val Nombre: String? = null,

    /*val DescripcionCorta: String? = null,
    val Fotografia1: String? = null,
    val IdPlaza: Int? = null,
    val CodigoPlaza: String? = null,
    val NombrePlaza: String? = null*/
){

    fun toZoneUI(): ZoneUI{
        return ZoneUI(
            id = IdZona,
            name = Nombre
        )
    }
}