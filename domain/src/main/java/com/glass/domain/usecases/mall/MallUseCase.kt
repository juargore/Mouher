package com.glass.domain.usecases.mall

import com.glass.domain.entities.LobbyData
import com.glass.domain.entities.MallData
import com.glass.domain.entities.TopBannerUI
import com.glass.domain.entities.TopTwoImagesUI
import com.glass.domain.repositories.IMallRepository
import io.reactivex.Observable

class MallUseCase(
    private val mallRepository: IMallRepository
): IMallUseCase {

    var mallData: MallData? = null

    override fun getTopBannerList(): Observable<List<TopBannerUI>> {

        return mallRepository
            .getAllMallData()
            .map { data ->
                this.mallData = data
                data.getTopBannerList()
            }
    }

    override fun getTwoTopImages(): Observable<TopTwoImagesUI> {
        return Observable.just(mallData?.getTopTwoImages())
    }

    override fun getLobbyData(): Observable<LobbyData> {
        return Observable.just(mallData?.getLobbyData())
    }
}