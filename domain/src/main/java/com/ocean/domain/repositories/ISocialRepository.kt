package com.ocean.domain.repositories

import com.ocean.domain.entities.Item
import io.reactivex.Observable

interface ISocialRepository {

    fun addOrUpdateSocialNetwork()

    fun getAllSocialNetworks(): Observable<List<Item>>

}