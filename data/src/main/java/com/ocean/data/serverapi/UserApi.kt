package com.ocean.data.serverapi

import com.ocean.domain.entities.*
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesOperaciones.php")
    fun addOrUpdateUser(@Field("WebService") WebService: String,
                        @Field("Id") Id: Int?,
                        @Field("Status") Status: Int,
                        @Field("Codigo") Codigo: String,
                        @Field("Nombre") Nombre: String,
                        @Field("ApellidoP") ApellidoP: String,
                        @Field("ApellidoM") ApellidoM: String,
                        @Field("Genero") Genero: Int,
                        @Field("FechaNac") FechaNac: String,
                        @Field("TelMovil") TelMovil: String,
                        @Field("Correo") Correo: String,
                        @Field("Contrasena") Contrasena: String
    ): Single<RegistrationData>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesOperaciones.php")
    fun loginUser(@Field("WebService") WebService: String,
                  @Field("Correo") Correo: String,
                  @Field("Contrasena") Contrasena: String
    ): Single<LoginData>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getFullUserData(@Field("WebService") WebService: String,
                        @Field("IdCliente") IdCliente: Int
    ): Single<UserProfileData>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesOperaciones.php")
    fun recoverPassword(@Field("WebService") WebService: String,
                        @Field("Correo") Correo: String
    ): Single<ResponseData>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesConsultas.php")
    fun getCountriesOrStatesList(@Field("WebService") WebService: String,
                                 @Field("IdPais") IdPais: Int?): Single<CountryStateData>


    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesOperaciones.php")
    fun addOrUpdateAddress(@Field("WebService") WebService: String,
                           @Field("Id") Id: Int?,
                           @Field("Status") Status: Int,
                           @Field("IdCliente") IdCliente: Int,
                           @Field("TipoDomicilio") TipoDomicilio: Int,
                           @Field("Calle") Calle: String,
                           @Field("NumInt") NumInt: String,
                           @Field("NumExt") NumExt: String,
                           @Field("Cruzamientos") Cruzamientos: String,
                           @Field("Colonia") Colonia: String,
                           @Field("CP") CP: String,
                           @Field("Pais") Pais: Int,
                           @Field("Estado") Estado: Int,
                           @Field("Municipio") Municipio: String
    ): Single<RegistrationData>
}