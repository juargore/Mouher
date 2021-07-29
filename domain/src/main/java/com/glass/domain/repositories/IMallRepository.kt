package com.glass.domain.repositories

import com.glass.domain.entities.MallData
import com.glass.domain.entities.SocialMediaData
import com.glass.domain.entities.StoresInZoneData
import io.reactivex.Observable

interface IMallRepository {

    fun getAllMallData(): Observable<MallData>

    fun getStoresByZone(zoneId: String): Observable<StoresInZoneData>

    fun getSocialMediaForMall(mallId: String): Observable<List<SocialMediaData>>

}