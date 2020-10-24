package com.glass.domain.entities

class MallData(
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
    val VestibuloTexto2Pos3: String? = null,
    val AcercaDeFotografiaPortada: String? = null,
    val AcercaDeFotografiaMedia: String? = null,
    val AcercaDeFotografiaCol1: String? = null,
    val AcercaDeFotografiaCol2: String? = null,
    val AcercaDeFotografiaCol3: String? = null,
    val AcercaDeFotografiaCol4: String? = null,
    val AcercaDeTitulo: String? = null,
    val AcercaDeTexto1: String? = null,
    val AcercaDeTexto2: String? = null,
    val AcercaDeTexto1Col1: String? = null,
    val AcercaDeTexto2Col1: String? = null,
    val AcercaDeTexto1Col2: String? = null,
    val AcercaDeTexto2Col2: String? = null,
    val AcercaDeTexto1Col3: String? = null,
    val AcercaDeTexto2Col3: String? = null,
    val AcercaDeTexto1Col4: String? = null,
    val AcercaDeTexto2Col4: String? = null
){

    fun getTopBannerList(): List<TopBannerUI>{
        val topBannerList = mutableListOf<TopBannerUI>()

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

        topBannerList.add(l1)
        topBannerList.add(l2)
        topBannerList.add(l3)

        return topBannerList
    }

}

