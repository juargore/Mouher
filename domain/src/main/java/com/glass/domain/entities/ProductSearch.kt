package com.glass.domain.entities

data class ProductSearchResponse(
    val Error: String,
    val Mensaje: String,
    val Productos: List<ProductSearchData>?
)

data class ProductSearchData(
    val IdTienda: Int,
    val NombreTienda: String,
    val IdProducto: Int,
    val NombreProducto: String,
    val DescripcionCorta: String,
    val PrecioActual: String,
    val PrecioAnterior: String? = null,
    val Fotografia: String? = null
) {
    fun toProductSearchUI() = ProductSearchUI(
        idStore = IdTienda,
        idProduct = IdProducto,
        name = NombreProducto,
        description = DescripcionCorta,
        urlImage = Fotografia,
        oldPrice = PrecioAnterior,
        currentPrice = PrecioActual
    )
}

data class ProductSearchUI(
    val idStore: Int,
    val idProduct: Int,
    val name: String,
    val description: String? = null,
    val urlImage: String? = null,
    val currentPrice: String? = null,
    val oldPrice: String? = null
)