package com.glass.data.serverapi

import com.glass.domain.entities.LoginData
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponseData
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    @FormUrlEncoded
    @POST("servicios/CServiciosAplicacionesOperaciones.php")
    fun registerNewUser(@Field("WebService") WebService: String,
                        @Field("Nombre") Nombre: String,
                        @Field("ApellidoP") ApellidoP: String,
                        @Field("ApellidoM") ApellidoM: String,
                        @Field("Genero") Genero: Int,
                        @Field("FechaNac") FechaNac: String,
                        @Field("TelMovil") TelMovil: Int,
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
    @POST("servicios/CServiciosAplicacionesOperaciones.php")
    fun recoverPassword(@Field("WebService") WebService: String,
                        @Field("Correo") Correo: String
    ): Single<ResponseData>

}