package com.ocean.data.repositories

import com.ocean.data.serverapi.MallApi
import com.ocean.domain.entities.AboutResponse
import com.ocean.domain.entities.MallData
import com.ocean.domain.entities.SocialMediaDataResponse
import com.ocean.domain.entities.StoresInZoneData
import com.ocean.domain.repositories.IMallRepository
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


    override fun getSocialMediaForMall(mallId: Int): Observable<SocialMediaDataResponse> {
        return api.getSocialMediaForMall(
            WebService = "ConsultaIntegralRedesPlaza",
            IdPlaza = "1"
        ).toObservable()
    }


    override fun getAboutInformation(storeId: Int?): Observable<AboutResponse> {
        return api.getMallAboutInformation(
            WebService = if(storeId == null) "ConsultaIntegralSobreNosotrosPlaza" else "ConsultaIntegralSobreNosotrosTienda",
            IdPlaza = if(storeId == null) "1" else null,
            IdTienda = storeId?.toString()
        ).toObservable()
    }
}