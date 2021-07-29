package com.glass.domain.usecases.store

import com.glass.domain.entities.CategoryUI
import com.glass.domain.entities.SponsorUI
import com.glass.domain.entities.TopBannerUI
import io.reactivex.Observable
import io.reactivex.Single

interface IStoreUseCase {

    fun getSponsorStoresByMallId(mallId: String): Observable<List<SponsorUI>>

    fun getStoreData(storeId: String): Observable<String>

    fun getTopBannerList(): Observable<List<TopBannerUI>>

    fun getStoreLogo(): Observable<String>

    fun getImageVideo(): Observable<String>

    fun getUrlVideo(): Observable<String>

    fun getCategoriesByStore(storeId: String): Single<List<CategoryUI>>
}