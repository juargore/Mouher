package com.glass.domain.usecases.mall

import com.glass.domain.entities.*
import io.reactivex.Observable
import io.reactivex.Single

interface IMallUseCase {

    fun getLogoImage(): Observable<String>

    fun getTopBannerList(): Observable<List<TopBannerUI>>

    fun getTwoTopImages(): Observable<TopTwoImagesUI>

    fun getLobbyData(): Observable<LobbyFullData>

    fun getZonesByMall(): Observable<List<ZoneUI>>

    fun getStoresByZone(zoneId: String): Observable<List<StoreInZoneUI>>

    fun getSocialMedia(): Observable<List<SocialMediaUI>>

    fun getAboutInformation(): Single<AboutUI>

    fun getContactInformation(): Single<ContactUI>
}