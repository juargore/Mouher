package com.glass.mouher.di

import com.glass.domain.common.ILogger
import com.glass.mouher.BuildConfig
import com.glass.mouher.database.DatabaseMigration
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

@Suppress("USELESS_CAST")
val DIExternals = module {
    single(DIConstants.Externals.RETROFIT.name) { createNetworkClient(
        context = androidContext(),
        baseUrl = BuildConfig.URL_PARENT
    )}

    single(DIConstants.Externals.REALM.name) {
        val config = RealmConfiguration.Builder()
            .schemaVersion(DatabaseMigration.latestVersion)
            .migration(DatabaseMigration())
            .build()
        Realm.getInstance(config)
    }
}