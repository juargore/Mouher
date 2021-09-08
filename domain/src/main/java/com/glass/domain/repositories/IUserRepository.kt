package com.glass.domain.repositories

import com.glass.domain.entities.LoginData
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponseData
import com.glass.domain.entities.UserProfileData
import io.reactivex.Single

interface IUserRepository {

    fun signIn(
        email: String,
        password: String
    ): Single<LoginData>


    fun addOrUpdateUser(
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
    ): Single<RegistrationData>


    fun getUserData(userId: Int): Single<UserProfileData>


    fun recoverPassword(
        email: String
    ): Single<ResponseData>
}