package com.glass.mouher.di

import com.glass.data.serverapi.*
import org.koin.dsl.module.module
import retrofit2.Retrofit

val DIAPIs = module {

    single(DIConstants.APIs.MALL.name) {(
            get(DIConstants.Externals.RETROFIT.name) as Retrofit).create(MallApi::class.java)
    }

    single(DIConstants.APIs.STORE.name) {(
            get(DIConstants.Externals.RETROFIT.name) as Retrofit).create(StoreApi::class.java)
    }

    single(DIConstants.APIs.PRODUCT.name) {(
            get(DIConstants.Externals.RETROFIT.name) as Retrofit).create(ProductApi::class.java)
    }

    single(DIConstants.APIs.USER.name) {(
            get(DIConstants.Externals.RETROFIT.name) as Retrofit).create(UserApi::class.java)
    }

    single(DIConstants.APIs.PAYMENT.name) {(
            get(DIConstants.Externals.RETROFIT.name) as Retrofit).create(PaymentApi::class.java)
    }
}