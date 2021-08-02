package com.glass.domain.entities

data class MallData(
    // Error section
    val Error: Int? = null,
    val Mensaje: String? = null,


    // Plaza comercial gral section
    val PlazaNombre: String? = null,
    val PlazaLogoColor: String? = null,
    val PlazaLogoBlanco: String? = null,


    // Banners top of Plaza comercial section
    val BannerFotoTop1: String? = null,
    val BannerLinkFotoTop1: String? = null,
    val BannerTituloFotoTop1: String? = null,
    val BannerSubtituloFotoTop1: String? = null,

    val BannerFotoTop2: String? = null,
    val BannerLinkFotoTop2: String? = null,
    val BannerTituloFotoTop2: String? = null,
    val BannerSubtituloFotoTop2: String? = null,

    val BannerFotoTop3: String? = null,
    val BannerLinkFotoTop3: String? = null,
    val BannerTituloFotoTop3: String? = null,
    val BannerSubtituloFotoTop3: String? = null,

    val BannerFotoTopIzq: String? = null,
    val BannerLinkFotoTopIzq: String? = null,
    val BannerFotoTopDer: String? = null,
    val BannerLinkFotoTopDer: String? = null,


    // Main Lobby of plaza comercial section
    val LobbyTitulo: String? = null,
    val LobbySubtitulo: String? = null,
    val LobbySubtituloInferior: String? = null,
    val LobbyTitulo1: String? = null,
    val LobbySubtitulo1: String? = null,
    val LobbyFoto1: String? = null,
    val LobbyLink1: String? = null,
    val LobbyTitulo2: String? = null,
    val LobbySubtitulo2: String? = null,
    val LobbyFoto2: String? = null,
    val LobbyLink2: String? = null,
    val LobbyTitulo3: String? = null,
    val LobbySubtitulo3: String? = null,
    val LobbyFoto3: String? = null,
    val LobbyLink3: String? = null,


    // Sponsors of plaza comercial section
    val Sponsors: List<SponsorData>? = null,


    // Sponsors of plaza comercial section
    val Zonas: List<ZoneData>? = null
){

    fun getTopBannerList(): List<TopBannerUI>{
        val l1 = TopBannerUI(
            id = "1",
            imageUrl = BannerFotoTop1,
            title = BannerTituloFotoTop1,
            subtitle = BannerSubtituloFotoTop1,
            linkToOpen = BannerLinkFotoTop1
        )

        val l2 = TopBannerUI(
            id = "2",
            imageUrl = BannerFotoTop2,
            title = BannerTituloFotoTop2,
            subtitle = BannerSubtituloFotoTop2,
            linkToOpen = BannerLinkFotoTop2
        )

        val l3 = TopBannerUI(
            id = "3",
            imageUrl = BannerFotoTop3,
            title = BannerTituloFotoTop3,
            subtitle = BannerSubtituloFotoTop3,
            linkToOpen = BannerLinkFotoTop3
        )

        return mutableListOf<TopBannerUI>()
            .apply {
                add(l1); add(l2); add(l3)
            }
    }

    fun getLobbyData(): LobbyFullData{
        val l1 = ItemLobby(
            urlImage = LobbyFoto1,
            title = LobbyTitulo1,
            subtitle = LobbySubtitulo1,
            linkToOpen = LobbyLink1
        )

        val l2 = ItemLobby(
            urlImage = LobbyFoto2,
            title = LobbyTitulo2,
            subtitle = LobbySubtitulo2,
            linkToOpen = LobbyLink2
        )

        val l3 = ItemLobby(
            urlImage = LobbyFoto3,
            title = LobbyTitulo3,
            subtitle = LobbySubtitulo3,
            linkToOpen = LobbyLink3
        )

        return LobbyFullData(
            title = LobbyTitulo,
            description = "$LobbySubtitulo\n$LobbySubtituloInferior",
            listItemsLobby = mutableListOf<ItemLobby>()
                .apply {
                    add(l2); add(l1); add(l3)
            }
        )
    }

    fun getTopTwoImages(): TopTwoImagesUI{
        return TopTwoImagesUI(
            urlImageTopLeft = BannerFotoTopIzq,
            urlImageTopRight = BannerFotoTopDer
        )
    }

    fun getMallLogoImage(): String? {
        return PlazaLogoBlanco
    }
}

