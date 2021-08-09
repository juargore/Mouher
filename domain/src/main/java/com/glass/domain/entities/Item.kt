package com.glass.domain.entities

data class Item(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    var price: Double? = null,
    var quantity: Int? = null,
    var valueClassification: String? = null
)