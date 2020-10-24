package com.glass.data.repositories.firestore

import com.glass.data.repositories.repositories.serverapi.MallApi
import com.glass.domain.common.or
import com.glass.domain.entities.MallData
import com.glass.domain.repositories.IMallRepository
import io.reactivex.Observable

class MallRepository(
    private val api: MallApi
): IMallRepository {

    override fun getAllMallData(): Observable<MallData> {
        return api.getAllDataForMall(
            WebService = "ConsultaCatPlazaId",
            IdBDD = "0",
            Id = "1"
        ).map { response->

            response.Datos?.let{ listData->
                listData[0]
            }.or {
                MallData()
            }

        }.toObservable()
    }
}