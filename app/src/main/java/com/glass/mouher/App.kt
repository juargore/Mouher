package com.glass.mouher

import android.app.Application
import com.glass.mouher.di.*
import org.koin.android.ext.android.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        //TODO: Add DIApi here:

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