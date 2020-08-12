package com.glass.domain.usecases.userProfile

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IUserProfileUseCase {

    fun getUserProfile(): Observable<Item>

    fun addOrUpdateUserProfile()
}