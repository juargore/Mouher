package com.glass.domain.entities

data class AboutResponse(
        val Error: Int? = null,
        val Mensaje: String? = null,
        val BannerFoto: String? = null,
        val Titulo: String? = null,
        val Parrafo1: String? = null,
        val Parrafo2: String? = null,
        val Colaborador1Nombre: String? = null,
        val Colaborador1Cargo: String? = null,
        val Colaborador1Foto: String? = null,
        val Colaborador2Nombre: String? = null,
        val Colaborador2Cargo: String? = null,
        val Colaborador2Foto: String? = null,
        val Colaborador3Nombre: String? = null,
        val Colaborador3Cargo: String? = null,
        val Colaborador3Foto: String? = null,
){
        fun getAboutUI(storeId: Int?): AboutUI{
                return AboutUI(
                        topImageUrl = BannerFoto,
                        topText = Titulo,
                        title = if(storeId == null) "¡BIENVENIDAS MOUHERES!" else "Algo sobre nosotros…",
                        subtitle = Titulo,
                        description = "$Parrafo1<br><br>$Parrafo2",
                        peopleList = getPeopleList()
                )
        }

        private fun getPeopleList(): List<AboutPersonUI>{
                return mutableListOf<AboutPersonUI>().apply {
                        add(AboutPersonUI(
                                name = Colaborador1Nombre,
                                imageUrl = Colaborador1Foto,
                                jobDescription = Colaborador1Cargo?.replaceBRwithBreakLine()
                        ))
                        add(AboutPersonUI(
                                name = Colaborador2Nombre,
                                imageUrl = Colaborador2Foto,
                                jobDescription = Colaborador2Cargo?.replaceBRwithBreakLine()
                        ))
                        add(AboutPersonUI(
                                name = Colaborador3Nombre,
                                imageUrl = Colaborador3Foto,
                                jobDescription = Colaborador3Cargo?.replaceBRwithBreakLine()
                        ))
                }
        }

        private fun String?.replaceBRwithBreakLine(): String?{
                return this?.replace("<br>", "\n")
        }
}


data class AboutUI(
        val topImageUrl: String? = null,
        val topText: String? = null,
        val title: String? = null,
        val subtitle: String? = null,
        val description: String? = null,
        val peopleList: List<AboutPersonUI>? = null
)


data class AboutPersonUI(
        val name: String? = null,
        val imageUrl: String? = null,
        val jobDescription: String? = null
)