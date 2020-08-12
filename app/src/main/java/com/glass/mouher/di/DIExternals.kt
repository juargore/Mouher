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
        baseUrl = BuildConfig.MOBILE_GATEWAY_URL
    )}

    single(DIConstants.Externals.LOGGER.name) { Logger.instance as ILogger }
    //single(DIConstants.Externals.REALM.name) {
        /*val config = RealmConfiguration.Builder()
            .schemaVersion(DatabaseMigration.latestVersion)
            .migration(DatabaseMigration())
            .build()
        Realm.getInstance(config)*/
    //}
}