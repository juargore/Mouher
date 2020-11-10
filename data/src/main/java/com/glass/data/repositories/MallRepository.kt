package com.glass.data.repositories

import com.glass.data.serverapi.MallApi
import com.glass.domain.common.or
import com.glass.domain.entities.MallData
import com.glass.domain.entities.SocialMediaData
import com.glass.domain.entities.StoreInZoneData
import com.glass.domain.entities.ZoneData
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

    override fun getZonesByMall(): Observable<List<ZoneData>> {
        return api.getZonesByMall(
            WebService = "ConsultaCatZonaIdPlaza",
            IdBDD = "0",
            IdPlaza = "1"
        ).map { response->

            response.Datos?.let{
                it
            }.or {
                emptyList()
            }

        }.toObservable()
    }

    override fun getStoresByZone(zoneId: String): Observable<List<StoreInZoneData>> {
        return api.getStoresInZone(
            WebService = "ConsultaCatTiendaIdZona",
            IdBDD = "0",
            IdZona = zoneId
        ).map { response->
            response.Datos?.let{
                it
            }.or {
                emptyList()
            }
        }.toObservable()
    }

    override fun getSocialMediaForMall(mallId: String): Observable<List<SocialMediaData>> {
        return api.getSocialMediaForMall(
            WebService = "ConsultaCatRedPlazaIdPlaza",
            IdBDD = "0",
            IdPlaza = mallId
        ).map { response ->
            response.Datos?.let{
                it
            }.or {
                emptyList()
            }
        }.toObservable()
    }
}