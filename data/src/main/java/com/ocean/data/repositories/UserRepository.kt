package com.ocean.data.repositories

import com.ocean.data.serverapi.UserApi
import com.ocean.domain.entities.*
import com.ocean.domain.repositories.IUserRepository
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
        code: String,
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
            Codigo = code,
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

    override fun getCountriesOrStates(getCountries: Boolean, countryId: Int?): Single<CountryStateData> {

        return api.getCountriesOrStatesList(
            WebService = if(getCountries) "ConsultaIntegralPaises" else "ConsultaIntegralEstados",
            IdPais = if(getCountries) null else countryId
        )
    }

    override fun addOrUpdateAddress(
        isUpdatingAddress: Boolean,
        id: Int?,
        userId: Int,
        addressType: Int,
        street: String,
        intNumber: String,
        extNumber: String,
        crosses: String,
        suburb: String,
        postalCode: String,
        countryId: Int,
        stateId: Int,
        municipality: String
    ): Single<RegistrationData> {

        return api.addOrUpdateAddress(
            WebService = "GuardaDomicilio",
            Id = if(isUpdatingAddress) id else 0,
            Status = if(isUpdatingAddress) 2 else 1,
            IdCliente = userId,
            TipoDomicilio = addressType,
            Calle = street,
            NumInt = intNumber,
            NumExt = extNumber,
            Cruzamientos = crosses,
            Colonia = suburb,
            CP = postalCode,
            Pais = countryId,
            Estado = stateId,
            Municipio = municipality
        )
    }
}