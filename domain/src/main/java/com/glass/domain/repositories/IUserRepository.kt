package com.glass.domain.repositories

import com.glass.domain.entities.LoginData
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponseData
import io.reactivex.Single

interface IUserRepository {

    fun signIn(
        email: String,
        password: String
    ): Single<LoginData>


    fun signUp(
        name: String,
        fLastName: String,
        mLastName: String,
        gender: Int,
        birthday: String,
        phone: Int,
        email: String,
        password: String
    ): Single<RegistrationData>


    fun recoverPassword(
        email: String
    ): Single<ResponseData>
}