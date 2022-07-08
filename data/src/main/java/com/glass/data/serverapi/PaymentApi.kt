package com.glass.data.serverapi

import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponsePaymentStatus
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PaymentApi {

    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesOperaciones.php")
    fun saveSaleFirstStep(@Field("WebService") WebService: String,
                          @Field("IdTienda") IdTienda: Int,
                          @Field("Observaciones") Observaciones: String,
                          @Field("IdCliente") IdCliente: Int,
                          @Field("Subtotal") Subtotal: String,
                          @Field("CostoEnvio") CostoEnvio: String,
                          @Field("Total") Total: String,
                          @Field("PagoTarjeta") PagoTarjeta: String,
                          @Field("FacturaRequerida") FacturaRequerida: Int,
                          @Field("RFC") RFC: String,
                          @Field("RazonSocial") RazonSocial: String,
                          @Field("Correo") Correo: String,
                          @Field("TipoApp") TipoApp: Int,
                          @Field("EnvioProveedor") EnvioProveedor: String,
                          @Field("EnvioServicio") EnvioServicio: String,
                          @Field("EnvioDescripcion") EnvioDescripcion: String,
                          @Field("EnvioEstimacionEntrega") EnvioEstimacionEntrega: String,
                          @Field("EnvioDimAlto") EnvioDimAlto: String, // send as int.toString()
                          @Field("EnvioDimAncho") EnvioDimAncho: String, // send as int.toString()
                          @Field("EnvioDimLargo") EnvioDimLargo: String, // send as int.toString()
                          @Field("EnvioPeso") EnvioPeso: String, // send as double.toString()
                          @Field("EnvioFragilidad") EnvioFragilidad: String,
    ): Single<RegistrationData> // returns same data as registration process


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesOperaciones.php")
    fun saveEachSaleSecondStep(@Field("WebService") WebService: String,
                               @Field("IdTienda") IdTienda: Int,
                               @Field("IdVenta") IdVenta: Int,
                               @Field("IdProducto") IdProducto: Int,
                               @Field("Numero") Numero: Int,
                               @Field("Cantidad") Cantidad: Int,
                               @Field("PrecioUnitario") PrecioUnitario: String,
                               @Field("Total") Total: String,
                               @Field("Observaciones") Observaciones: String
    ): Single<RegistrationData> // returns same data as registration process


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getPaymentStatus(@Field("WebService") WebService: String,
                         @Field("IdTienda") IdTienda: String,
                         @Field("IdVenta") IdVenta: String
    ): Single<ResponsePaymentStatus>

}