package com.ocean.domain.usecases.mall

import com.ocean.domain.entities.*
import io.reactivex.Observable

interface IMallUseCase {

    fun triggerToGetAllMallData(): Observable<Unit>

    fun getZonesForMenu(): Observable<List<ZoneUI>>

    fun getLogoImage(): Observable<String>

    fun getTopBannerList(): Observable<List<TopBannerUI>>

    fun getTwoTopImages(): Observable<TopTwoImagesUI>

    fun getSponsorsByMallId(mallId: String): Observable<List<SponsorUI>>

    fun getLobbyData(): Observable<LobbyFullData>

    fun getZonesByMall(): Observable<List<ZoneUI>>

    fun getStoresByZone(zoneId: String): Observable<List<StoreInZoneUI>>

    fun getSocialMedia(mallId: Int): Observable<List<SocialMediaUI>>

    fun getAboutInformation(storeId: Int?): Observable<AboutUI>

    fun getContactInformation(mallId: Int): Observable<ContactUI>
}