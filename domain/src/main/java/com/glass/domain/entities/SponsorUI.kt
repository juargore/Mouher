package com.glass.domain.entities

data class SponsorUI(
    val id: String? = null,
    val name: String? = null,
    var urlImage: String? = null,
    var willOpenExternalUrl: Boolean = false,
    var linkToOpen: String? = null,
    var idOfStoreToOpen: Int? = null,
)