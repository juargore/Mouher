package com.glass.mouher

import android.app.Application
import com.glass.mouher.di.*
import io.realm.Realm
import org.koin.android.ext.android.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        startKoin(this,
            listOf(
                DIExternals,
                DIHelpers,
                DIRepositories,
                DIUseCases,
                DIViewModel,
                DIAPIs
            ))
    }
}