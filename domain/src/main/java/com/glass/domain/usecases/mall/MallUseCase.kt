package com.glass.domain.usecases.mall

import com.glass.domain.entities.*
import com.glass.domain.repositories.IMallRepository
import io.reactivex.Observable

class MallUseCase(
    private val mallRepository: IMallRepository
): IMallUseCase {

    var mallData: MallData? = null

    override fun getTopBannerList(): Observable<List<TopBannerUI>> {

        return mallRepository
            .getAllMallData()
            .map { data ->
                this.mallData = data
                data.getTopBannerList()
            }
    }

    override fun getTwoTopImages(): Observable<TopTwoImagesUI> {
        return Observable.just(mallData?.getTopTwoImages())
    }

    override fun getLobbyData(): Observable<LobbyData> {
        return Observable.just(mallData?.getLobbyData())
    }

    override fun getZonesByMall(): Observable<List<ZoneUI>> {

        return mallRepository
            .getZonesByMall()
            .map { zonesList->
                val mList = mutableListOf<ZoneUI>()

                zonesList.forEach {
                    mList.add(it.toZoneUI())
                }

                return@map mList
            }
    }

    override fun getStoresByZone(zoneId: String): Observable<List<StoreInZoneUI>> {
        return mallRepository
            .getStoresByZone(zoneId)
            .map { storeDataList->
                val mList = mutableListOf<StoreInZoneUI>()

                storeDataList.forEach {
                    mList.add(it.getStoreInZoneUI())
                }

                return@map mList
            }
    }
}