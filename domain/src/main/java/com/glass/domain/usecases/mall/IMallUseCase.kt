package com.glass.domain.usecases.mall

import com.glass.domain.entities.*
import io.reactivex.Observable

interface IMallUseCase {

    fun getTopBannerList(): Observable<List<TopBannerUI>>

    fun getTwoTopImages(): Observable<TopTwoImagesUI>

    fun getLobbyData(): Observable<LobbyData>

    fun getZonesByMall(): Observable<List<ZoneUI>>

    fun getStoresByZone(zoneId: String): Observable<List<StoreInZoneUI>>
}