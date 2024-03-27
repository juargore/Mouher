package com.ocean.mouher.di

import com.ocean.mouher.BuildConfig
import com.ocean.mouher.database.DatabaseMigration
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val DIExternals = module {
    single(DIConstants.Externals.RETROFIT.name) { createNetworkClient(
        context = androidContext(),
        baseUrl = BuildConfig.URL_PARENT
    )}

    single(DIConstants.Externals.REALM.name) {
        val config = RealmConfiguration.Builder()
            .schemaVersion(DatabaseMigration.latestVersion)
            .allowWritesOnUiThread(true)
            .migration(DatabaseMigration())
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.getInstance(config)
    }
}
