package com.glass.domain.usecases.store

import com.glass.domain.entities.SponsorStoreUI
import com.glass.domain.repositories.IStoreRepository
import io.reactivex.Observable

class StoreUseCase(
    private val storeRepository: IStoreRepository
): IStoreUseCase {

    override fun getSponsorStoresByMall(): Observable<List<SponsorStoreUI>> {
        return storeRepository.getSponsorStoresByMall()
    }

}