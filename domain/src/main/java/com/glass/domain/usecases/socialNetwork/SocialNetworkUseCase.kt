package com.glass.domain.usecases.socialNetwork

import com.glass.domain.entities.Item
import com.glass.domain.repositories.ISocialNetworkRepository
import io.reactivex.Observable

class SocialNetworkUseCase(
    private val socialNetworkRepository: ISocialNetworkRepository
): ISocialNetworkUseCase {

    override fun addOrUpdateSocialNetwork() {}

    override fun getAllSocialNetworks(): Observable<List<Item>> = socialNetworkRepository.getAllSocialNetworks()

}