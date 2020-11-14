package com.glass.data.serverapi

import com.glass.domain.entities.ResponseFullProduct
import com.glass.domain.entities.ResponseNewArrivals
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ProductApi {

    @FormUrlEncoded
    @POST("models/catalogos/CRegProductoNuevoConsulta.php")
    fun getNewArrivalsForStore(@Field("WebService") WebService: String,
                               @Field("IdBDD") IdBDD: String): Single<ResponseNewArrivals>


    @FormUrlEncoded
    @POST("models/catalogos/CCatProductoConsulta.php")
    fun getFullProductData(@Field("WebService") WebService: String,
                           @Field("IdBDD") IdBDD: String,
                           @Field("Id") Id: String): Single<ResponseFullProduct>


    @FormUrlEncoded
    @POST("models/catalogos/CCatProductoConsultaCustom.php")
    fun getProductListByCategory(@Field("WebService") WebService: String,
                                 @Field("IdBDD") IdBDD: String): Single<ResponseFullProduct>
}