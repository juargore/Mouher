package com.glass.domain.entities

data class StoreData(
    // Error section
    val Error: Int? = null,
    val Mensaje: String? = null,


    // Store gral information sectino
    val TiendaNombre: String? = null,
    val TiendaLogoColor: String? = null,
    val TiendaLogoGris: String? = null,
    val Video: String? = null,
    val VideoImagenFondo: String? = null,


    // Banners section
    val BannerFoto1: String? = null,
    val BannerTituloFoto1: String? = null,
    val BannerSubtituloFoto1: String? = null,
    val BannerFoto2: String? = null,
    val BannerTituloFoto2: String? = null,
    val BannerSubtituloFoto2: String? = null,
    val BannerFoto3: String? = null,
    val BannerTituloFoto3: String? = null,
    val BannerSubtituloFoto3: String? = null,


    // Categories list
    val Categorias: List<CategoryData>? = null,


    // Deal of the day list
    val OfertaDia: List<ProductData>? = null,


    // New arrivals on products list
    val NuevasLlegadas: List<ProductData>? = null,


    // Sponsors list
    val Sponsors: List<SponsorData>? = null
) {

    fun getTopBannerList(): List<TopBannerUI>{
        val l1 = TopBannerUI(
            id = "1",
            imageUrl = BannerFoto1,
            title = BannerTituloFoto1,
            subtitle = BannerSubtituloFoto1
        )

        val l2 = TopBannerUI(
            id = "2",
            imageUrl = BannerFoto2,
            title = BannerTituloFoto2,
            subtitle = BannerSubtituloFoto2
        )

        val l3 = TopBannerUI(
            id = "3",
            imageUrl = BannerFoto3,
            title = BannerTituloFoto3,
            subtitle = BannerSubtituloFoto3
        )

        return mutableListOf<TopBannerUI>()
            .apply {
                add(l1); add(l2); add(l3)
            }
    }

    fun getStoreLogoImage(): String? = TiendaLogoColor

    fun getImageVideo(): String? = VideoImagenFondo

    fun getLinkForVideo(): String? = Video
}