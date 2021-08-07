package com.glass.data.serverapi

import com.glass.domain.entities.*
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ProductApi {

    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getProductListByCategory(@Field("WebService") WebService: String,
                          @Field("IdTienda") IdTienda: String,
                          @Field("IdValorClasif1") IdValorClasif1: String): Single<ProductByCategoryData>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getFullProduct(@Field("WebService") WebService: String,
                       @Field("IdTienda") IdTienda: String,
                       @Field("IdProducto") IdProducto: String): Single<FullProductDataResponse>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesOperaciones.php")
    fun saveNewReviewForProduct(@Field("WebService") WebService: String,
                                @Field("IdTienda") IdTienda: String,
                                @Field("IdProducto") IdProducto: String,
                                @Field("Nombre") Nombre: String,
                                @Field("Correo") Correo: String,
                                @Field("Reseña") Reseña: String,
                                @Field("Valoracion") Valoracion: String): Single<ResponseData>
}