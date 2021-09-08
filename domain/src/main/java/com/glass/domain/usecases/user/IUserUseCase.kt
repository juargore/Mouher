package com.glass.domain.usecases.user

import com.glass.domain.entities.LoginData
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponseUI
import com.glass.domain.entities.UserProfileData
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

    fun recoverPassword(email: String): Single<ResponseUI>
}