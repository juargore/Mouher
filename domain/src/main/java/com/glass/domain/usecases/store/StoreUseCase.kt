package com.glass.domain.usecases.store

import com.glass.domain.entities.*
import com.glass.domain.repositories.IStoreRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class StoreUseCase(
    private val storeRepository: IStoreRepository
): IStoreUseCase {

    private var storeData: StoreData? = null

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

    @Suppress("CheckResult")
    override fun getStoreData(storeId: String): Observable<Unit> {
        return storeRepository
            .getAllStoreData(storeId)
            .observeOn(Schedulers.io())
            .map { this.storeData = it }
    }

    override fun getTopBannerList(): Observable<List<TopBannerUI>> {
        return Observable.just(storeData?.getTopBannerList())
    }

    override fun getStoreLogo(): Observable<String> {
        return Observable.just(storeData?.getStoreLogoImage())
    }

    override fun getImageVideo(): Observable<String> {
        return Observable.just(storeData?.getImageVideo())
    }

    override fun getUrlVideo(): Observable<String> {
        return Observable.just(storeData?.getLinkForVideo())
    }

    private fun getImageForStore(storeId: String?): String{
        return storeRepository.getImageForSponsorStore(storeId ?: "0")
            .subscribeOn(Schedulers.io())
            .blockingFirst()
    }

}