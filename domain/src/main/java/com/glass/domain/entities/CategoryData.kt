package com.glass.domain.entities

data class CategoryData(
    val IdCategoria: Int? = null,
    val Nombre: String? = null,
    val Descripcion: String? = null,
    val Imagen: String? = null
){

    fun toCategoryUI(): CategoryUI{
        return CategoryUI(
            categoryId = IdCategoria,
            name = Nombre,
            description = Descripcion,
            imageUrl = Imagen
        )
    }
}


data class CategoryUI(
    val categoryId: Int? = null,
    val name: String? = null,
    val description: String? = null,
    var imageUrl: String? = null
)