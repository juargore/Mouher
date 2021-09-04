package com.glass.domain.usecases.user

import com.glass.domain.entities.LoginData
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.ResponseUI
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
        phone: Int,
        email: String,
        password: String ): Single<RegistrationData> {

        return userRepository.signUp(name, fLastName, mLastName, gender, birthday, phone, email, password)
    }

    override fun recoverPassword(email: String): Single<ResponseUI> {
        return userRepository.recoverPassword(email).map { it.toResponseUI() }
    }
}