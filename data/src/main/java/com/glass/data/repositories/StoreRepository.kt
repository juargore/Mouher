package com.glass.data.repositories

import com.glass.data.serverapi.StoreApi
import com.glass.domain.common.or
import com.glass.domain.entities.SponsorData
import com.glass.domain.entities.StoreData
import com.glass.domain.repositories.IStoreRepository
import io.reactivex.Observable

class StoreRepository(
    private val api: StoreApi
): IStoreRepository {

    override fun getSponsorStoresByMall(): Observable<List<SponsorData>> {

        return api.getSponsorStoresByMall(
            WebService = "ConsultaRegEnlacePlazaIdPlaza",
            IdBDD = "0",
            IdPlaza = "1")
            .map {  response->

                response.datoes?.let{ listData->
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

    override fun getAllStoreData(storeId: String): Observable<StoreData> {
        return api.getDataForStore(
            WebService = "ConsultaCatTiendaId",
            IdBDD = "0",
            Id = storeId
        )
            .map { response ->
                response.Datos?.let{
                    it[0]
                }.or {
                    StoreData()
                }
            }.toObservable()
    }

}