package com.glass.domain.usecases.userProfile

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IUserProfileRepository
import io.reactivex.Observable

class UserProfileUseCase(
    private val userProfileRepository: IUserProfileRepository
): IUserProfileUseCase {

    init {
        userProfileRepository.startRegistration("arturo.g.resendiz@gmail.com")
    }

    override fun getUserProfile(): Observable<Item> = userProfileRepository.getUserProfile()

    override fun addOrUpdateUserProfile() {}
}