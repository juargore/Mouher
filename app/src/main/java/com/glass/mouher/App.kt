package com.glass.mouher

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.glass.mouher.di.*
import io.realm.Realm
import org.koin.android.ext.android.startKoin

class App: Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        context = this
        Realm.init(this)

        startKoin(this,
            listOf(
                DIExternals,
                DIRepositories,
                DIUseCases,
                DIViewModel,
                DIAPIs
            ))
    }
}