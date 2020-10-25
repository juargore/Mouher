package com.glass.data.repositories.repositories.serverapi

import com.glass.domain.entities.ResponseMall
import com.glass.domain.entities.ResponseZone
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MallApi {

    @FormUrlEncoded
    @POST("models/catalogos/CCatPlazaConsulta.php")
    fun getAllDataForMall(@Field("WebService") WebService: String,
                          @Field("IdBDD") IdBDD: String,
                          @Field("Id") Id: String): Single<ResponseMall>

    @FormUrlEncoded
    @POST("models/catalogos/CCatZonaConsulta.php")
    fun getZonesByMall(@Field("WebService") WebService: String,
                       @Field("IdBDD") IdBDD: String,
                       @Field("IdPlaza") IdPlaza: String): Single<ResponseZone>
}