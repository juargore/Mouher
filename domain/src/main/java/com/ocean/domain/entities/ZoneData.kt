package com.ocean.domain.entities

data class ZoneData(
    val IdZona: Int? = null,
    val Nombre: String? = null,
) {
    fun toZoneUI() = ZoneUI(id = IdZona, name = Nombre)
}

data class ZoneUI(
    val id: Int? = null,
    val name: String? = null,
    var urlImage: String? = null,
    val description: String? = null
)