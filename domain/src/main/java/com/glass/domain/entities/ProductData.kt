package com.glass.domain.entities

data class ProductData(
    val IdProducto: Int? = null,
    val Nombre: String? = null,
    val Descripcion: String? = null,
    val Fotografia: String? = null,
    val PrecioActual: String? = null,
    val PrecioAnterior: String? = null,
    val Rating: Int? = null
){

    fun toProductUI(): ProductUI{
        return ProductUI(
            id = IdProducto,
            name = Nombre,
            sidePhoto1 = Fotografia,
            currentPrice = PrecioActual,
            oldPrice = PrecioAnterior,
            description = Descripcion,
            rating = Rating
        )
    }
}


data class ProductUI(
    val id: Int? = null,
    val name: String? = null,
    var sidePhoto1: String? = null,
    var sidePhoto2: String? = null,
    var sidePhoto3: String? = null,
    var sidePhoto4: String? = null,
    var sidePhoto5: String? = null,
    val currentPrice: String? = null,
    val oldPrice: String? = null,
    val description: String? = null,
    val rating: Int? = null,
    val discount: String? = null,
    val extraInfoTitle: String? = null,
    val extraInfoDesc: String? = null,
    val key1ForTable: String? = null,
    val value1ForTable: String? = null,
    val key2ForTable: String? = null,
    val value2ForTable: String? = null,
    val key3ForTable: String? = null,
    val value3ForTable: String? = null
)