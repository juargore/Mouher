package com.glass.domain.usecases.store

import com.glass.domain.entities.SponsorStoreUI
import com.glass.domain.repositories.IStoreRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class StoreUseCase(
    private val storeRepository: IStoreRepository
): IStoreUseCase {

    override fun getSponsorStoresByMall(): Observable<List<SponsorStoreUI>> {

        return storeRepository
            .getSponsorStoresByMall()
            .map { listData->
                val nList = mutableListOf<SponsorStoreUI>()

                listData.forEach { store ->
                    nList.add(store.getSponsorStoreUI())
                }
                return@map nList
            }
            .map { listUI->

                listUI.forEach {
                    it.urlImage = getImageForStore(it.id)
                }
                return@map listUI
            }

    }

    private fun getImageForStore(storeId: String?): String{
        return storeRepository.getImageForSponsorStore(storeId ?: "0")
            .subscribeOn(Schedulers.io())
            .blockingFirst()
    }

}