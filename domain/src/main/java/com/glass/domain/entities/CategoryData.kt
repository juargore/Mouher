package com.glass.domain.entities

data class CategoryData (
    val IdCategoria: Int? = null,
    val Nombre: String? = null,
    val DescripcionCorta: String? = null,
    val Imagen: String? = null
){
    fun toCategoryUI() = CategoryUI(
        categoryId = IdCategoria,
        name = Nombre,
        description = DescripcionCorta,
        imageUrl = Imagen
    )
}


data class CategoryUI (
    val categoryId: Int? = null,
    val name: String? = null,
    val description: String? = null,
    var imageUrl: String? = null
)