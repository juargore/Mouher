package com.glass.domain.entities

data class ProductUI(
    val id: String? = null,
    val name: String? = null,
    var sidePhoto1: String? = null,
    var sidePhoto2: String? = null,
    var sidePhoto3: String? = null,
    var sidePhoto4: String? = null,
    var sidePhoto5: String? = null,
    val price: String? = null,
    val description: String? = null,
    val rating: String? = null,
    val extraInfoTitle: String? = null,
    val extraInfoDesc: String? = null,
    val key1ForTable: String? = null,
    val value1ForTable: String? = null,
    val key2ForTable: String? = null,
    val value2ForTable: String? = null,
    val key3ForTable: String? = null,
    val value3ForTable: String? = null
)