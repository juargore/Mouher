package com.glass.domain.entities

data class StoreData(
    val Status: Int? = null,
    val Nombre: String? = null,
    val DescripcionCorta: String? = null,
    val DescripcionLarga: String? = null,
    val Fotografia1: String? = null,
    val Fotografia2: String? = null,
    val Fotografia3: String? = null,
    val FotografiaLogo1: String? = null,
    val FotografiaLogo2: String? = null,
    val IdZona: Int? = null,
    val FotografiaVid1: String? = null,
    val EnlaceVid1: String? = null
) {

    fun getTopBannerList(): List<TopBannerUI>{
        val l1 = TopBannerUI(
            id = "1",
            imageUrl = Fotografia1,
            linkToOpen = "",
            title = DescripcionCorta,
            subtitle = Nombre
        )

        val l2 = TopBannerUI(
            id = "2",
            imageUrl = Fotografia2,
            linkToOpen = "",
            title = DescripcionCorta,
            subtitle = Nombre
        )

        val l3 = TopBannerUI(
            id = "3",
            imageUrl = Fotografia3,
            linkToOpen = "",
            title = DescripcionCorta,
            subtitle = Nombre
        )

        return mutableListOf<TopBannerUI>()
            .apply {
                add(l1); add(l2); add(l3)
            }
    }

    fun getStoreLogoImage(): String? = FotografiaLogo1

    fun getImageVideo(): String? = FotografiaVid1

    fun getLinkForVideo(): String? = EnlaceVid1
}