package com.glass.data.repositories.firestore

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IUserProfileRepository
import io.reactivex.Observable

class UserProfileRepository(

): IUserProfileRepository {
    override fun startRegistration(userId: String) {

    }

    override fun getUserProfile(): Observable<Item> {
        return Observable.just(Item())
    }

    override fun addOrUpdateUserProfile() {

    }


}