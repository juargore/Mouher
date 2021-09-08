package com.glass.data.repositories

import com.glass.data.serverapi.UserApi
import com.glass.domain.entities.LoginData
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponseData
import com.glass.domain.entities.UserProfileData
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

    override fun addOrUpdateUser(
        isUpdatingUser: Boolean,
        userId: Int?,
        name: String,
        fLastName: String,
        mLastName: String,
        gender: Int,
        birthday: String,
        phone: String,
        email: String,
        password: String
    ): Single<RegistrationData> {

        return api.addOrUpdateUser(
            WebService = "GuardaCliente",
            Id = if(isUpdatingUser) userId else 0,
            Status = 1,
            Codigo = "",
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

    override fun getUserData(userId: Int): Single<UserProfileData> {
        return api.getFullUserData(
            WebService = "ConsultaIntegralCliente",
            IdCliente = userId
        )
    }

    override fun recoverPassword(email: String): Single<ResponseData> {

        return api.recoverPassword(
            WebService = "RecuperaContrasenaCliente",
            Correo = email
        )
    }
}