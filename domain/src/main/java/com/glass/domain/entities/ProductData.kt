package com.glass.domain.entities

data class ProductData(
    val Id: String? = null,
    val Status: String? = null,
    val Codigo: String? = null,
    val Nombre: String? = null,
    val DescripcionCorta: String? = null,
    val DescripcionLarga: String? = null,
    val Fotografia1: String? = null,
    val Fotografia2: String? = null,
    val Fotografia3: String? = null,
    val Fotografia4: String? = null,
    val Fotografia5: String? = null,
    val PrecioVenta1: String? = null,
    val PrecioVenta2: String? = null,
    val PrecioVenta3: String? = null,
    val PrecioCompra1: String? = null,
    val PrecioCompra2: String? = null,
    val PrecioCompra3: String? = null,
    val PrecioAnterior1: String? = null,
    val PrecioAnterior2: String? = null,
    val FactorConver1: String? = null,
    val FactorConver2: String? = null,
    val FactorConver3: String? = null,
    val FotografiaAdicional1: String? = null,
    val FotografiaAdicional2: String? = null,
    val FotografiaAdicional3: String? = null,
    val Valoracion1: String? = null,
    val Valoracion2: String? = null,
    val Valoracion3: String? = null,
    val Etiquetas: String? = null,
    val Video1: String? = null,
    val Video2: String? = null,
    val Video3: String? = null,
    val FotografiaVid1: String? = null,
    val FotografiaVid2: String? = null,
    val FotografiaVid3: String? = null,
    val EnlaceVid1: String? = null,
    val EnlaceVid2: String? = null,
    val EnlaceVid3: String? = null,
    val ExtraSeccion1Titulo: String? = null,
    val ExtraSeccion1Texto: String? = null,
    val ExtraSeccion1Fotografia1: String? = null,
    val ExtraSeccion1Fotografia2: String? = null,
    val ExtraSeccion1Fotografia3: String? = null,
    val ExtraSeccion2Linea1Titulo: String? = null,
    val ExtraSeccion2Linea1Texto: String? = null,
    val ExtraSeccion2Linea2Titulo: String? = null,
    val ExtraSeccion2Linea2Texto: String? = null,
    val ExtraSeccion2Linea3Titulo: String? = null,
    val ExtraSeccion2Linea3Texto: String? = null
) {

    fun getProductUI(): ProductUI{
        return ProductUI(
            id = Id,
            name = Nombre,
            sidePhoto1 = Fotografia1,
            sidePhoto2 = Fotografia2,
            sidePhoto3 = Fotografia3,
            sidePhoto4 = Fotografia4,
            sidePhoto5 = Fotografia5,
            price = PrecioVenta1,
            description = DescripcionLarga,
            rating = "",
            extraInfoTitle = ExtraSeccion1Titulo,
            extraInfoDesc = ExtraSeccion1Texto,
            key1ForTable = ExtraSeccion2Linea1Titulo,
            value1ForTable = ExtraSeccion2Linea1Texto,
            key2ForTable = ExtraSeccion2Linea2Titulo,
            value2ForTable = ExtraSeccion2Linea2Texto,
            key3ForTable = ExtraSeccion2Linea3Titulo,
            value3ForTable = ExtraSeccion2Linea3Texto
        )
    }


    fun getShortProductUI(): ShortProductUI{
        return ShortProductUI(
            id = Id,
            imageUrl = Fotografia1,
            category = "",
            name = Nombre,
            rating = "",
            price = PrecioVenta1,
            oldPrice = PrecioAnterior1
        )
    }
}