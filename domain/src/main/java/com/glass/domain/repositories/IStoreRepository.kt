package com.glass.domain.repositories

import com.glass.domain.entities.SponsorData
import com.glass.domain.entities.StoreData
import io.reactivex.Observable

interface IStoreRepository {

    fun getSponsorStoresByMall(): Observable<List<SponsorData>>

    fun getImageForSponsorStore(storeId: String): Observable<String>

    fun getAllStoreData(storeId: String): Observable<StoreData>

}