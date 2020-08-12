package com.glass.domain.repositories

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface ISocialNetworkRepository {

    fun addOrUpdateSocialNetwork()

    fun getAllSocialNetworks(): Observable<List<Item>>

}