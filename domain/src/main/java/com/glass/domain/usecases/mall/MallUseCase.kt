package com.glass.domain.usecases.mall

import com.glass.domain.entities.TopBannerUI
import com.glass.domain.repositories.IMallRepository
import io.reactivex.Observable

class MallUseCase(
    private val mallRepository: IMallRepository
): IMallUseCase {

    override fun getTopBannerList(): Observable<List<TopBannerUI>> {

        return mallRepository
            .getAllMallData()
            .map { mallData ->
                mallData.getTopBannerList()
            }
    }
}