package com.ocean.domain.entities

data class ProductByCategoryData(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val BannerFoto: String? = null,
    val BannerTituloFoto: String? = null,
    val BannerSubtituloFoto: String? = null,
    val Productos: List<ProductData>? = null
){
    fun getScreenTopInfo() = ScreenTopInformationUI(
        urlImageTop = BannerFoto,
        titleTop = BannerTituloFoto,
        subTitleTop = BannerSubtituloFoto
    )
}


data class ScreenTopInformationUI(
    val urlImageTop: String? = null,
    val titleTop: String? = null,
    val subTitleTop: String? = null
)