package com.glass.domain.entities

data class ProductSearchUI(
    val idStore: Int,
    val idProduct: Int,
    val name: String,
    val description: String? = null,
    val urlImage: String? = null
)
