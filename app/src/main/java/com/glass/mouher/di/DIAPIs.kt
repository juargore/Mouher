package com.glass.mouher.di

import com.glass.data.repositories.repositories.serverapi.MallApi
import org.koin.dsl.module.module
import retrofit2.Retrofit

val DIAPIs = module {

    single(DIConstants.APIs.MALL.name) {(
            get(DIConstants.Externals.RETROFIT.name) as Retrofit).create(MallApi::class.java
        )
    }
}