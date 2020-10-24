package com.glass.domain.usecases.mall

import com.glass.domain.entities.LobbyData
import com.glass.domain.entities.TopBannerUI
import com.glass.domain.entities.TopTwoImagesUI
import io.reactivex.Observable

interface IMallUseCase {

    fun getTopBannerList(): Observable<List<TopBannerUI>>

    fun getTwoTopImages(): Observable<TopTwoImagesUI>

    fun getLobbyData(): Observable<LobbyData>
}