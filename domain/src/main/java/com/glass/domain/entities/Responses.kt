package com.glass.domain.entities

data class ResponseSponsorStores(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val datoes: List<SponsorData>?
)

data class ResponseSponsorImageStore(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<SponsorImageData>?
)

data class ResponseStoresInZone(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val datoes: List<StoresInZoneData>?
)

data class ResponseStore(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<StoreData>?
)

data class ResponseSocialMedia(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<SocialMediaData>?
)

data class ResponseNewArrivals(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<NewArrivalProductData>?
)

data class ResponseFullProduct(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<ProductData>?
)