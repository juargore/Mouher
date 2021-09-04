package com.glass.data.repositories

import com.glass.data.serverapi.UserApi
import com.glass.domain.entities.LoginData
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponseData
import com.glass.domain.repositories.IUserRepository
import io.reactivex.Single

class UserRepository(
    private val api: UserApi
): IUserRepository {

    override fun signIn(email: String, password: String): Single<LoginData> {
        return api.loginUser(
            WebService = "VerificaCredencialesCliente",
            Correo = email,
            Contrasena = password
        )
    }

    override fun signUp(
        name: String,
        fLastName: String,
        mLastName: String,
        gender: Int,
        birthday: String,
        phone: Int,
        email: String,
        password: String
    ): Single<RegistrationData> {

        return api.registerNewUser(
            WebService = "GuardaCliente",
            Nombre = name,
            ApellidoP = fLastName,
            ApellidoM = mLastName,
            Genero = gender,
            FechaNac = birthday,
            TelMovil = phone,
            Correo = email,
            Contrasena = password
        )
    }

    override fun recoverPassword(email: String): Single<ResponseData> {

        return api.recoverPassword(
            WebService = "RecuperaContrasenaCliente",
            Correo = email
        )
    }
}