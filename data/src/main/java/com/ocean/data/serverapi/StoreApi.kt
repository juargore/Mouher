package com.ocean.data.serverapi

import com.ocean.domain.entities.*
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StoreApi {

    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getDataForStore(@Field("WebService") WebService: String,
                        @Field("IdTienda") IdTienda: String): Single<StoreData>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesOperaciones.php")
    fun subscribeUserToNewsletter(@Field("WebService") WebService: String,
                                  @Field("IdTienda") IdTienda: String,
                                  @Field("Nombre") Nombre: String,
                                  @Field("Correo") Correo: String): Single<ResponseData>

}