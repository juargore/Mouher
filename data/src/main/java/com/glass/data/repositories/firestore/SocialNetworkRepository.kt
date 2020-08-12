package com.glass.data.repositories.firestore

import com.glass.domain.entities.Item
import com.glass.domain.repositories.ISocialNetworkRepository
import io.reactivex.Observable

class SocialNetworkRepository(

): ISocialNetworkRepository {
    override fun addOrUpdateSocialNetwork() {

    }

    override fun getAllSocialNetworks(): Observable<List<Item>> {
        return Observable.just(emptyList())
    }

}