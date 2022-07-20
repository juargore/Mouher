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


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getHistoryProducts(@Field("WebService") WebService: String,
                           @Field("IdCliente") IdCliente: Int,
                           @Field("FechaInicial") FechaInicial: String,
                           @Field("FechaFinal") FechaFinal: String): Single<HistoryResponse>

    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun searchProductByWord(@Field("WebService") WebService: String,
                            @Field("Etiquetas") Etiquetas: String): Single<ProductSearchResponse>

    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesProcesos.php")
    fun getParcelsPrices(@Field("WebService") WebService: String,
                         @Field("IdTienda") IdTienda: Int,
                         @Field("IdCliente") IdCliente: Int,
                         @Field("IdsProductosCSV") IdsProductosCSV: String,
                         @Field("CantidadesCSV") CantidadesCSV: String): Single<ParcelsResponse>
}