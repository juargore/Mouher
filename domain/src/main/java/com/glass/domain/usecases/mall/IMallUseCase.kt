package com.glass.domain.usecases.mall

import com.glass.domain.entities.TopBannerUI
import io.reactivex.Observable

interface IMallUseCase {

    fun getTopBannerList(): Observable<List<TopBannerUI>>

}