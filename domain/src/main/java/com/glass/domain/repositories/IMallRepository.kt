package com.glass.domain.repositories

import com.glass.domain.entities.MallData
import com.glass.domain.entities.ZoneData
import io.reactivex.Observable

interface IMallRepository {

    fun getAllMallData(): Observable<MallData>

    fun getZonesByMall(): Observable<List<ZoneData>>

}