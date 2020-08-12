package com.glass.mouher.di

import com.glass.data.repositories.helpers.RemoteConfigHelper
import com.glass.domain.helpers.IRemoteConfigHelper
import org.koin.dsl.module.module

@Suppress("USELESS_CAST")
val DIHelpers = module {

    single(DIConstants.Helpers.REMOTE_CONFIG.name) { RemoteConfigHelper(
        remoteConfig = get(DIConstants.Externals.FIREBASE_REMOTE_CONFIG.name),
        logger = get(DIConstants.Externals.LOGGER.name)
    ) as IRemoteConfigHelper
    }
}