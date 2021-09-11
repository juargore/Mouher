package com.glass.domain.usecases.user

import com.glass.domain.entities.*
import io.reactivex.Single

interface IUserUseCase {

    fun signIn(email: String, password: String): Single<LoginData>

    fun signUp(
        name: String,
        fLastName: String,
        mLastName: String,
        gender: Int,
        birthday: String,
        phone: String,
        email: String,
        password: String
    ): Single<RegistrationData>

    fun updateUser(
        id: Int,
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

    fun getGenderList(): List<String>

    fun recoverPassword(email: String): Single<ResponseUI>

    fun getCountriesAsList(): Single<List<Country>>

    fun getStatesAsList(countryId: Int): Single<List<State>>

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