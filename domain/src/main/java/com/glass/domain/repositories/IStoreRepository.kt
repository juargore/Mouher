package com.glass.domain.repositories

import com.glass.domain.entities.SponsorStoreData
import io.reactivex.Observable

interface IStoreRepository {

    fun getSponsorStoresByMall(): Observable<List<SponsorStoreData>>

    fun getImageForSponsorStore(storeId: String): Observable<String>

}