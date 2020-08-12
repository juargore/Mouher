package com.glass.domain.repositories

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IUserProfileRepository {

    fun startRegistration(userId: String)

    fun getUserProfile(): Observable<Item>

    fun addOrUpdateUserProfile()
}