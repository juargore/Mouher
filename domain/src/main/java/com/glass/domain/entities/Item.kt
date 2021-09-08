package com.glass.domain.entities

/** Class used on UI to parse the data from internal database */
data class Item(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    var price: Double? = null,
    var quantity: Int? = null,
    var valueClassification: String? = null, // values such as Piel, Negro, 25, etc
    var storeId: Int? = null
)