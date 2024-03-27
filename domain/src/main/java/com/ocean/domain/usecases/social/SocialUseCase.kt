package com.ocean.domain.usecases.social

import com.ocean.domain.entities.Item
import com.ocean.domain.repositories.ISocialRepository
import io.reactivex.Observable

class SocialUseCase(
    private val socialRepository: ISocialRepository
): ISocialUseCase {

    override fun addOrUpdateSocialNetwork() {}

    override fun getAllSocialNetworks(): Observable<List<Item>> = socialRepository.getAllSocialNetworks()

}