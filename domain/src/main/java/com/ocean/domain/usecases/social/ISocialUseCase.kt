package com.ocean.domain.usecases.social

import com.ocean.domain.entities.Item
import io.reactivex.Observable

interface ISocialUseCase {

    @Suppress("EmptyMethod")
    fun addOrUpdateSocialNetwork()

    fun getAllSocialNetworks(): Observable<List<Item>>
}