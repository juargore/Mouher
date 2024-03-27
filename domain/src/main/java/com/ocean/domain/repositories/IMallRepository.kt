package com.ocean.domain.repositories

import com.ocean.domain.entities.*
import io.reactivex.Observable

interface IMallRepository {

    fun getAllMallData(): Observable<MallData>

    fun getStoresByZone(zoneId: String): Observable<StoresInZoneData>

    fun getSocialMediaForMall(mallId: Int): Observable<SocialMediaDataResponse>

    fun getAboutInformation(storeId: Int?): Observable<AboutResponse>

}