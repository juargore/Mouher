package com.glass.data.repositories.repositories.firestore

import com.glass.data.repositories.repositories.serverapi.StoreApi
import com.glass.domain.common.or
import com.glass.domain.entities.SponsorStoreData
import com.glass.domain.repositories.IStoreRepository
import io.reactivex.Observable

class StoreRepository(
    private val api: StoreApi
): IStoreRepository {

    override fun getSponsorStoresByMall(): Observable<List<SponsorStoreData>> {

        return api.getSponsorStoresByMall(
            WebService = "ConsultaRegEnlacePlazaIdPlaza",
            IdBDD = "0",
            IdPlaza = "1")
            .map {  response->

                response.Datos?.let{ listData->
                    listData
                }.or {
                    emptyList()
                }

            }.toObservable()
    }

    override fun getImageForSponsorStore(storeId: String): Observable<String> {
        return api.getImageForSponsorStore(
            WebService = "ConsultaCatTiendaId",
            IdBDD = "0",
            Id = storeId)
            .map {  response->

                response.Datos?.let{ listData->
                    listData[0].FotografiaLogo1
                }.or{
                    ""
                }

            }.toObservable()
    }

}