package com.glass.domain.usecases.social

import com.glass.domain.entities.Item
import com.glass.domain.repositories.ISocialRepository
import io.reactivex.Observable

class SocialUseCase(
    private val socialRepository: ISocialRepository
): ISocialUseCase {

    override fun addOrUpdateSocialNetwork() {}

    override fun getAllSocialNetworks(): Observable<List<Item>> = socialRepository.getAllSocialNetworks()

}