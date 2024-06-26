package com.ocean.domain.usecases.store

import com.ocean.domain.entities.*
import io.reactivex.Observable

interface IStoreUseCase {

    fun triggerToGetStoreData(storeId: Int): Observable<Unit>

    fun getTopBannerList(): Observable<List<TopBannerUI>>

    fun getShippingInfoStore(): Observable<String>

    fun getStoreLogo(): Observable<String>

    fun getImageVideo(): Observable<String>

    fun getUrlVideo(): Observable<String>

    fun getNewArrivalsForStore(): Observable<List<ProductUI>>

    fun getCategoriesByStore(): Observable<List<CategoryUI>>

    fun getStoreSimpleInformation(): Observable<String>

    fun getSponsorsByStore(): Observable<List<SponsorUI>>

    fun subscribeUserToNewsletter(userName: String, email: String, storeId: Int): Observable<ResponseUI>

}