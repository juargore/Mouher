package com.glass.domain.repositories

import com.glass.domain.entities.SponsorStoreUI
import io.reactivex.Observable

interface IStoreRepository {

    fun getSponsorStoresByMall(): Observable<List<SponsorStoreUI>>

}