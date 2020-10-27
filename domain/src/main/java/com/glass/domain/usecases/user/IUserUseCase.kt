package com.glass.domain.usecases.user

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IUserUseCase {

    fun getUserProfile(): Observable<Item>

    fun addOrUpdateUserProfile()
}