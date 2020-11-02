package com.glass.data.serverapi

import com.glass.domain.entities.ResponseSponsorImageStore
import com.glass.domain.entities.ResponseSponsorStores
import com.glass.domain.entities.ResponseStore
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StoreApi {

    @FormUrlEncoded
    @POST("models/catalogos/CRegEnlacePlazaConsulta.php")
    fun getSponsorStoresByMall(@Field("WebService") WebService: String,
                               @Field("IdBDD") IdBDD: String,
                               @Field("IdPlaza") IdPlaza: String): Single<ResponseSponsorStores>

    @FormUrlEncoded
    @POST("models/catalogos/CCatTiendaConsulta.php")
    fun getImageForSponsorStore(@Field("WebService") WebService: String,
                                @Field("IdBDD") IdBDD: String,
                                @Field("Id") Id: String): Single<ResponseSponsorImageStore>

    @FormUrlEncoded
    @POST("models/catalogos/CCatTiendaConsulta.php")
    fun getDataForStore(@Field("WebService") WebService: String,
                        @Field("IdBDD") IdBDD: String,
                        @Field("Id") Id: String): Single<ResponseStore>

}