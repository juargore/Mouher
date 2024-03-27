package com.ocean.domain.entities

data class ProductData(
    val IdProducto: Int? = null,
    val Nombre: String? = null,
    val DescripcionCorta: String? = null,
    val DescripcionLarga: String? = null,
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
            description = DescripcionCorta,
            fullDescription = DescripcionLarga,
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
    val fullDescription: String? = null,
    val rating: Int? = null,
    val discount: String? = null,
    val productType: Int? = null,

    // Classification values
    val classificationTitle1: String? = null,
    val classificationValues1AsString: String? = null,
    val classificationValues1: List<String>? = null,
    val classificationTitle2: String? = null,
    val classificationValues2AsString: String? = null,
    val classificationValues2: List<String>? = null,
    val classificationTitle3: String? = null,
    val classificationValues3AsString: String? = null,
    val classificationValues3: List<String>? = null,
    val classificationValuesQty: Int? = null
)


data class FullProductDataResponse(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val BannerFoto: String? = null,
    val BannerTituloFoto: String? = null,
    val BannerSubtituloFoto: String? = null,
    val Nombre: String? = null,
    val DescripcionCorta: String? = null,
    val DescripcionLarga: String? = null,
    val FullDescription: String? = null,
    val Fotografia1: String? = null,
    val Fotografia2: String? = null,
    val Fotografia3: String? = null,
    val Fotografia4: String? = null,
    val Fotografia5: String? = null,
    val PrecioActual: String? = null,
    val PrecioAnterior: String? = null,
    val Rating: Int? = null,
    val Existencia: Int? = null,
    val TipoProducto: Int? = null,

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
        description = DescripcionCorta,
        fullDescription = DescripcionLarga,
        rating = Rating,
        classificationTitle1 = Clasificacion1,
        classificationValues1AsString = ValoresClasificacion1,
        classificationValues1 = getValuesAsList(ValoresClasificacion1),
        classificationTitle2 = Clasificacion2,
        classificationValues2AsString = ValoresClasificacion2,
        classificationValues2 = getValuesAsList(ValoresClasificacion2),
        classificationTitle3 = Clasificacion3,
        classificationValues3AsString = ValoresClasificacion3,
        classificationValues3 = getValuesAsList(ValoresClasificacion3),
        classificationValuesQty = Existencia,
        productType = TipoProducto
    )

    fun getTopInformation(): ScreenTopInformationUI =
        ScreenTopInformationUI(
            urlImageTop = BannerFoto,
            titleTop = BannerTituloFoto,
            subTitleTop = BannerSubtituloFoto
        )

    private fun getValuesAsList(value: String?): List<String> {
        value?.let {
            return it
                .replace(" ", "")
                .split(",")
                .toTypedArray()
                .toList()
        }

        return emptyList()
    }
}
