package com.glass.domain.usecases.mall

import com.glass.domain.entities.*
import com.glass.domain.repositories.IMallRepository
import io.reactivex.Observable
import io.reactivex.Single

class MallUseCase(
    private val mallRepository: IMallRepository
): IMallUseCase {

    private var mallData: MallData? = null

    override fun triggerToGetAllMallData(): Observable<Unit> {
        return mallRepository.getAllMallData().map {
            mallData = it
        }
    }

    override fun getTopBannerList(): Observable<List<TopBannerUI>> {
        return Observable.just(mallData?.getTopBannerList())
    }

    override fun getLogoImage(): Observable<String> {
        return Observable.just(mallData?.getMallLogoImage())
    }

    override fun getTwoTopImages(): Observable<TopTwoImagesUI> {
        return Observable.just(mallData?.getTopTwoImages())
    }

    override fun getSponsorsByMallId(mallId: String): Observable<List<SponsorUI>> {
        val mList = mutableListOf<SponsorUI>()

        mallData?.Sponsors?.forEach {
            mList.add(it.getSponsorStoreUI())
        }

        return Observable.just(mList)
    }

    override fun getLobbyData(): Observable<LobbyFullData> {
        return Observable.just(mallData?.getLobbyData())
    }

    override fun getZonesByMall(): Observable<List<ZoneUI>> {
        val mList = mutableListOf<ZoneUI>()

        mallData?.Zonas?.forEach {
            mList.add(it.toZoneUI())
        }

        return Observable.just(mList)
    }

    override fun getStoresByZone(zoneId: String): Observable<List<StoreInZoneUI>> {
        return mallRepository
            .getStoresByZone(zoneId)
            .map { storeDataList->
                val mList = mutableListOf<StoreInZoneUI>()

                storeDataList.Tiendas?.forEach {
                    mList.add(it.getStoreInZoneUI())
                }

                return@map mList
            }
    }

    override fun getSocialMedia(): Observable<List<SocialMediaUI>> {
        return mallRepository
            .getSocialMediaForMall("1")
            .map { socialDataList ->
                val mList = mutableListOf<SocialMediaUI>()

                socialDataList.forEach {
                    mList.add(it.getSocialMediaUI())
                }

                return@map mList
            }
    }

    override fun getAboutInformation(): Single<AboutUI> {
        val mList = mutableListOf<AboutPersonUI>()
        mList.add(AboutPersonUI(name = "Magaly Fregoso", position = "Directora General", partner = "Socio fundador", imageUrl = "http://desarrollo01.mouhermarket.com/admin/uploads/tienda0/Equipo_Magaly.jpg"))
        mList.add(AboutPersonUI(name = "Magaly Fregoso", position = "Directora Comercial", partner = "Socio fundador", imageUrl = "http://desarrollo01.mouhermarket.com/admin/uploads/tienda0/Equipo_Jennie.jpg"))
        mList.add(AboutPersonUI(name = "Magaly Fregoso", position = "Director de Sistemas", partner = "Socio fundador", imageUrl = "http://desarrollo01.mouhermarket.com/admin/uploads/tienda0/Equipo_Fabian.jpg"))


        return Single.just(AboutUI(
                topImageUrl = "https://static.zara.net/photos//mkt/spots/aw20-north-shoes-bags-woman/subhome-xmedia-33//landscape_0.jpg?ts=1597317424891&imwidth=1366",
                topText = "Sobre nosotros",
                title = "¡BIENVENIDAS MOUHERES!",
                subtitle = "¿Quiénes somos?",
                description = "Mouher Market es una Plataforma Mexicana Potencializadora de Marcas Creadas o Comercializadas por Mujeres.\n" +
                        "\n" +
                        "Nuestro objetivo es unificar en un sólo lugar los esfuerzos de Mujeres Emprendedoras para vender sus productos proyectándolos en el mercado online, construyendo un canal de venta directo entre emprendedoras y clientes potenciales de todo el mundo por medio de una tienda virtual, buscando ayudar a las mujeres a obtener mayores ganancias y aportar en su bienestar integral.\n" +
                        " \n" +
                        "Mouher Market surge de la idea de querer ayudar y potencializarnos como mujeres. Existen muchas mujeres que han emprendido o queremos emprender con productos muy buenos para comercializar, sin embargo, muchas de las veces no tenemos los recursos o las herramientas para impulsar nuestros negocios o darlos a conocer.\n" +
                        "\n" +
                        "A través de Mouher Market queremos brindar la oportunidad de tener un espacio atractivo y creativo con la misión de potencializar y enaltecer a nuestras emprendedoras.",
                peopleList = mList
        ))
    }

    override fun getContactInformation(): Single<ContactUI> {
        return Single.just(ContactUI(
                address = "Colombia 1856, Puerto Vallarta",
                phone = "3221216060",
                email = "contacto@mouhermarket.com",
                workHours = "09:00 a 16:00, de lunes a viernes",
                urlOportunities = "http://desarrollo01.mouhermarket.com/",
                urlPrivacyPolicy = "http://desarrollo01.mouhermarket.com/",
                urlTermsAndConditions = "http://desarrollo01.mouhermarket.com/"
        ))
    }
}