package com.glass.domain.usecases.social

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface ISocialUseCase {

    fun addOrUpdateSocialNetwork()

    fun getAllSocialNetworks(): Observable<List<Item>>
}