package com.glass.domain.usecases.store

import com.glass.domain.entities.*
import com.glass.domain.repositories.IStoreRepository
import io.reactivex.Observable

class StoreUseCase(
    private val storeRepository: IStoreRepository
): IStoreUseCase {

    private var storeData: StoreData? = null

    override fun triggerToGetStoreData(storeId: Int): Observable<Unit> {
        return storeRepository.getAllStoreData(storeId).map { storeData = it }
    }

    override fun getSponsorStoresByMallId(mallId: String): Observable<List<SponsorUI>> {
        return Observable.just(listOf())
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

    override fun getNewArrivalsForStore(): Observable<List<ProductUI>> {
        val mList = mutableListOf<ProductUI>()
        storeData?.NuevasLlegadas?.forEach {
            mList.add(it.toProductUI())
        }

        return Observable.just(mList)
    }

    override fun getCategoriesByStore(): Observable<List<CategoryUI>> {
        val mList = mutableListOf<CategoryUI>()
        storeData?.Categorias?.forEach{
            mList.add(it.toCategoryUI())
        }

        return Observable.just(mList)
    }

    override fun getSponsorsByStore(): Observable<List<SponsorUI>> {
        val mList = mutableListOf<SponsorUI>()

        storeData?.Sponsors?.forEach {
            mList.add(it.getSponsorStoreUI())
        }

        return Observable.just(mList)
    }

    override fun subscribeUserToNewsletter(
        userName: String,
        email: String,
        storeId: Int
    ): Observable<ResponseUI> {
        return storeRepository.subscribeUserToNewsletter(userName, email, storeId).map {
            return@map it.toResponseUI()
        }
    }

}