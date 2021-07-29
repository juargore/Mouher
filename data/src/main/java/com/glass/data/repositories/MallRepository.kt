package com.glass.data.repositories

import com.glass.data.serverapi.MallApi
import com.glass.domain.common.or
import com.glass.domain.entities.MallData
import com.glass.domain.entities.SocialMediaData
import com.glass.domain.entities.StoresInZoneData
import com.glass.domain.repositories.IMallRepository
import io.reactivex.Observable

class MallRepository(
    private val api: MallApi
): IMallRepository {

    override fun getAllMallData(): Observable<MallData> {
        return api.getAllDataForMall(
            WebService = "ConsultaIntegralPlaza",
            IdPlaza = "1"
        ).toObservable()
    }

    override fun getStoresByZone(zoneId: String): Observable<StoresInZoneData> {
        return api.getStoresInZone(
            WebService = "ConsultaIntegralTiendasPorZona",
            IdZona = zoneId
        ).toObservable()
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