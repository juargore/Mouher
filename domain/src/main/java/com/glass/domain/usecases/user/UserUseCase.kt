package com.glass.domain.usecases.user

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IUserRepository
import io.reactivex.Observable

class UserUseCase(
    private val userProfileRepository: IUserRepository
): IUserUseCase {

    override fun getUserProfile(): Observable<Item> = Observable.just(Item())

    override fun addOrUpdateUserProfile() {}
}