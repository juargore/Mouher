package com.glass.domain.usecases.user

import com.glass.domain.entities.LoginData
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponseUI
import com.glass.domain.entities.UserProfileData
import com.glass.domain.repositories.IUserRepository
import io.reactivex.Single

class UserUseCase(
    private val userRepository: IUserRepository
): IUserUseCase {

    override fun signIn(email: String, password: String): Single<LoginData> {
        return userRepository.signIn(email, password)
    }

    override fun signUp(
        name: String,
        fLastName: String,
        mLastName: String,
        gender: Int,
        birthday: String,
        phone: String,
        email: String,
        password: String ): Single<RegistrationData> {

        return userRepository.addOrUpdateUser(false, null, name, fLastName, mLastName, gender, birthday, phone, email, password)
    }

    override fun updateUser(
        id: Int,
        name: String,
        fLastName: String,
        mLastName: String,
        gender: Int,
        birthday: String,
        phone: String,
        email: String,
        password: String
    ): Single<RegistrationData> {
        return userRepository.addOrUpdateUser(true, id, name, fLastName, mLastName, gender, birthday, phone, email, password)
    }

    override fun getUserData(userId: Int): Single<UserProfileData> {
        return userRepository.getUserData(userId)
    }

    override fun recoverPassword(email: String): Single<ResponseUI> {
        return userRepository.recoverPassword(email).map { it.toResponseUI() }
    }
}