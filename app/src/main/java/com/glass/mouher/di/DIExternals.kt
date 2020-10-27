package com.glass.mouher.di

import com.glass.domain.common.ILogger
import com.glass.mouher.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

@Suppress("USELESS_CAST")
val DIExternals = module {
    single(DIConstants.Externals.FIREBASE_STORAGE.name) { createStorage() }
    single(DIConstants.Externals.RETROFIT.name) { createNetworkClient(
        context = androidContext(),
        baseUrl = BuildConfig.URL_PARENT
    )}

    single(DIConstants.Externals.LOGGER.name) { Logger.instance as ILogger }
}