package com.glass.domain.repositories

import com.glass.domain.entities.*
import io.reactivex.Single

interface IUserRepository {

    fun signIn(
        email: String,
        password: String
    ): Single<LoginData>


    fun addOrUpdateUser(
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
    ): Single<RegistrationData>


    fun getUserData(userId: Int): Single<UserProfileData>


    fun recoverPassword(
        email: String
    ): Single<ResponseData>

    fun getCountriesOrStates(getCountries: Boolean, countryId: Int?): Single<CountryStateData>

    fun addOrUpdateAddress(
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
    ): Single<RegistrationData>
}