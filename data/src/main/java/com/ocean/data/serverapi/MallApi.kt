package com.ocean.data.serverapi

import com.ocean.domain.entities.*
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MallApi {

    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getAllDataForMall(@Field("WebService") WebService: String,
                          @Field("IdPlaza") IdPlaza: String): Single<MallData>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getStoresInZone(@Field("WebService") WebService: String,
                        @Field("IdZona") IdZona: String): Single<StoresInZoneData>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getSocialMediaForMall(@Field("WebService") WebService: String,
                              @Field("IdPlaza") IdPlaza: String): Single<SocialMediaDataResponse>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getMallAboutInformation(@Field("WebService") WebService: String,
                                @Field("IdTienda") IdTienda: String?,
                                @Field("IdPlaza") IdPlaza: String?): Single<AboutResponse>
}