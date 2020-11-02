package com.glass.domain.entities

data class ResponseMall(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<MallData>?
)

data class ResponseZone(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<ZoneData>?
)

data class ResponseSponsorStores(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<SponsorStoreData>?
)

data class ResponseSponsorImageStore(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<SponsorImageData>?
)

data class ResponseStoresInZone(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<StoreInZoneData>?
)

data class ResponseStore(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Datos: List<StoreData>?
)