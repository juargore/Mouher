package com.glass.domain.usecases.store

import com.glass.domain.entities.SponsorStoreUI
import com.glass.domain.entities.TopBannerUI
import io.reactivex.Completable
import io.reactivex.Observable

interface IStoreUseCase {

    fun getSponsorStoresByMall(): Observable<List<SponsorStoreUI>>

    fun getStoreData(storeId: String): Observable<Unit>

    fun getTopBannerList(): Observable<List<TopBannerUI>>

    fun getStoreLogo(): Observable<String>

    fun getImageVideo(): Observable<String>

    fun getUrlVideo(): Observable<String>
}