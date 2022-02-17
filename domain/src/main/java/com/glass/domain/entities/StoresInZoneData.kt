package com.glass.domain.entities

data class StoresInZoneData(
    // Error section
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Tiendas: List<StoreZoneData>? = null
)

data class StoreZoneData(
    val IdTienda: Int? = null,
    val Nombre: String? = null,
    val Imagen: String? = null,
    val Productos: Int? = null
){
    fun getStoreInZoneUI() = StoreInZoneUI(
        id = IdTienda,
        urlImage = Imagen,
        name = Nombre,
        totalProducts = Productos
    )
}

data class StoreInZoneUI(
    val id: Int? = null,
    var urlImage: String? = null,
    val name: String? = null,
    val totalProducts: Int? = null
)