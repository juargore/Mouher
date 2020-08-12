package com.glass.domain.usecases.socialNetwork

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface ISocialNetworkUseCase {

    fun addOrUpdateSocialNetwork()

    fun getAllSocialNetworks(): Observable<List<Item>>
}