package com.glass.domain.repositories

import com.glass.domain.entities.MallData
import io.reactivex.Observable

interface IMallRepository {

    fun getAllMallData(): Observable<MallData>

}