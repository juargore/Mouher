package com.glass.domain.entities

data class MallData(
    val Nombre: String? = null,
    val Fotografia1: String? = null,
    val Fotografia2: String? = null,
    val Fotografia3: String? = null,
    val Fotografia4: String? = null,
    val Fotografia5: String? = null,
    val FotografiaLogo1: String? = null,
    val FotografiaLogo2: String? = null,
    val Texto1Portada1: String? = null,
    val Texto2Portada1: String? = null,
    val Texto1Portada2: String? = null,
    val Texto2Portada2: String? = null,
    val Texto1Portada3: String? = null,
    val Texto2Portada3: String? = null,
    val VestibuloTitulo: String? = null,
    val VestibuloTexto1: String? = null,
    val VestibuloTexto2: String? = null,
    val VestibuloFotografiaPos1: String? = null,
    val VestibuloFotografiaPos2: String? = null,
    val VestibuloFotografiaPos3: String? = null,
    val VestibuloTexto1Pos1: String? = null,
    val VestibuloTexto2Pos1: String? = null,
    val VestibuloTexto1Pos2: String? = null,
    val VestibuloTexto2Pos2: String? = null,
    val VestibuloTexto1Pos3: String? = null,
    val VestibuloTexto2Pos3: String? = null
){

    fun getLobbyData(): LobbyFullData{
        val l1 = ItemLobby(
            urlImage = VestibuloFotografiaPos1,
            title = VestibuloTexto1Pos1,
            subtitle = VestibuloTexto2Pos1
        )

        val l2 = ItemLobby(
            urlImage = VestibuloFotografiaPos2,
            title = VestibuloTexto1Pos2,
            subtitle = VestibuloTexto2Pos2
        )

        val l3 = ItemLobby(
            urlImage = VestibuloFotografiaPos3,
            title = VestibuloTexto1Pos3,
            subtitle = VestibuloTexto2Pos3
        )

        return LobbyFullData(
            title = VestibuloTitulo,
            description = "$VestibuloTexto1 $VestibuloTexto2",
            listItemsLobby = mutableListOf<ItemLobby>()
                .apply {
                    add(l2); add(l1); add(l3)
            }
        )
    }

    fun getTopBannerList(): List<TopBannerUI>{
        val l1 = TopBannerUI(
            id = "1",
            imageUrl = Fotografia1,
            linkToOpen = "",
            title = Texto1Portada1,
            subtitle = Texto2Portada1
        )

        val l2 = TopBannerUI(
            id = "2",
            imageUrl = Fotografia2,
            linkToOpen = "",
            title = Texto1Portada2,
            subtitle = Texto2Portada2
        )

        val l3 = TopBannerUI(
            id = "3",
            imageUrl = Fotografia3,
            linkToOpen = "",
            title = Texto1Portada3,
            subtitle = Texto2Portada3
        )

        return mutableListOf<TopBannerUI>()
            .apply {
                add(l1); add(l2); add(l3)
        }
    }

    fun getTopTwoImages(): TopTwoImagesUI{
        return TopTwoImagesUI(
            urlImageTopLeft = Fotografia4,
            urlImageTopRight = Fotografia5
        )
    }

    fun getMallLogoImage(): String? {
        return FotografiaLogo2
    }
}

