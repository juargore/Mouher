package com.glass.domain.usecases.store

import com.glass.domain.entities.SponsorStoreUI
import io.reactivex.Observable

interface IStoreUseCase {

    fun getSponsorStoresByMall(): Observable<List<SponsorStoreUI>>

}