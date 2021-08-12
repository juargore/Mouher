package com.glass.domain.repositories

import com.glass.domain.entities.*
import io.reactivex.Observable

interface IMallRepository {

    fun getAllMallData(): Observable<MallData>

    fun getStoresByZone(zoneId: String): Observable<StoresInZoneData>

    fun getSocialMediaForMall(mallId: Int): Observable<SocialMediaDataResponse>

    fun getAboutInformation(storeId: Int?): Observable<AboutResponse>

}