package com.ocean.domain.usecases.mall

import com.ocean.domain.entities.*
import com.ocean.domain.repositories.IMallRepository
import io.reactivex.Observable

class MallUseCase(
    private val mallRepository: IMallRepository
): IMallUseCase {

    private var mallData: MallData? = null

    override fun triggerToGetAllMallData(): Observable<Unit> {
        return mallRepository.getAllMallData().map {
            mallData = it // just to assign value to globar var
        }
    }

    override fun getZonesForMenu(): Observable<List<ZoneUI>> {
        return mallRepository.getAllMallData().map {
            val mList = mutableListOf<ZoneUI>()

            mallData?.Zonas?.forEach {
                mList.add(it.toZoneUI())
            }

            return@map mList
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

    override fun getSocialMedia(mallId: Int): Observable<List<SocialMediaUI>> {
        return mallRepository
            .getSocialMediaForMall(mallId)
            .map { socialDataList ->
                val mList = mutableListOf<SocialMediaUI>()

                socialDataList.Redes?.forEach {
                    mList.add(it.getSocialMediaUI())
                }

                return@map mList
            }
    }

    override fun getAboutInformation(storeId: Int?): Observable<AboutUI> {
        return mallRepository.getAboutInformation(storeId)
            .map { return@map it.getAboutUI(storeId) }
    }

    override fun getContactInformation(mallId: Int): Observable<ContactUI> {
        return Observable.just(mallData?.getContactInformation())
    }
}