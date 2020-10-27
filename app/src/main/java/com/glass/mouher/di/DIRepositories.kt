package com.glass.mouher.di

import com.glass.data.repositories.repositories.firestore.MallRepository
import com.glass.data.repositories.repositories.firestore.StoreRepository
import com.glass.domain.repositories.*
import org.koin.dsl.module.module

@Suppress("USELESS_CAST")
val DIRepositories = module {

    single{ MallRepository(
        api = get(DIConstants.APIs.MALL.name)
    ) as IMallRepository }

    single{ StoreRepository(
        api = get(DIConstants.APIs.STORE.name)
    ) as IStoreRepository }
}