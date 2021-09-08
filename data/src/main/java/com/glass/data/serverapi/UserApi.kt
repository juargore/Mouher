package com.glass.data.serverapi

import com.glass.domain.entities.LoginData
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponseData
import com.glass.domain.entities.UserProfileData
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

}