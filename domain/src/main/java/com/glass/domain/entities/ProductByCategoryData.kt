package com.glass.domain.entities

data class ProductByCategoryData(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Productos: List<ProductData>? = null
)
