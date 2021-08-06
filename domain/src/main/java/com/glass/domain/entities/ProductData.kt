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


data class FullProductDataResponse(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val BannerFoto: String? = null,
    val BannerTituloFoto: String? = null,
    val BannerSubtituloFoto: String? = null,
    val Nombre: String? = null,
    val Descripcion: String? = null,
    val Fotografia1: String? = null,
    val Fotografia2: String? = null,
    val Fotografia3: String? = null,
    val Fotografia4: String? = null,
    val Fotografia5: String? = null,
    val PrecioActual: String? = null,
    val PrecioAnterior: String? = null,
    val Rating: Int? = null,

    val Clasificacion1: String? = null,
    val ValoresClasificacion1: String? = null,
    val Clasificacion2: String? = null,
    val ValoresClasificacion2: String? = null,
    val Clasificacion3: String? = null,
    val ValoresClasificacion3: String? = null,

    val ProductosOfrecidos: List<ProductData>? = null,
    val ProductosRelacionados: List<ProductData>? = null,
    val Reseñas: List<ReviewData>? = null
){
    fun getProductUI() = ProductUI(
        name = Nombre,
        sidePhoto1 = Fotografia1,
        sidePhoto2 = Fotografia2,
        sidePhoto3 = Fotografia3,
        sidePhoto4 = Fotografia4,
        sidePhoto5 = Fotografia5,
        currentPrice = PrecioActual,
        oldPrice = PrecioAnterior,
        description = Descripcion,
        rating = Rating,

    )
}
