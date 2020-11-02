package com.glass.domain.repositories

import com.glass.domain.entities.SponsorStoreData
import com.glass.domain.entities.StoreData
import io.reactivex.Observable

interface IStoreRepository {

    fun getSponsorStoresByMall(): Observable<List<SponsorStoreData>>

    fun getImageForSponsorStore(storeId: String): Observable<String>

    fun getAllStoreData(storeId: String): Observable<StoreData>
}