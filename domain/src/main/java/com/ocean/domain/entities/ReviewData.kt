package com.ocean.domain.entities

data class ReviewData(
        val IdReseña: Int? = null,
        val Nombre: String? = null,
        val Reseña: String? = null,
        val Fecha: String? = null,
        val Estrellas: Int? = null
){
        fun toReviewUI() = ReviewUI(
                id = IdReseña,
                userName = Nombre,
                rating = Estrellas,
                review = Reseña,
                date = Fecha
        )
}

data class ReviewUI(
        val id: Int? = null,
        val userName: String? = null,
        val date: String? = null,
        val rating: Int? = null,
        val review: String? = null,
        val urlPhoto: String? = null
)