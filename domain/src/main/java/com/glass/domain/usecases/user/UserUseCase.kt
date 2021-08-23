package com.glass.domain.usecases.user

import com.glass.domain.entities.Item
import io.reactivex.Observable

class UserUseCase(

): IUserUseCase {

    override fun getUserProfile(): Observable<Item> = Observable.just(Item())

    override fun addOrUpdateUserProfile() {}

    override fun signIn(email: String, password: String) {

    }

    override fun signUp() {

    }

    override fun recoverPassword() {

    }
}