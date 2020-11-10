package com.glass.mouher.di

import com.glass.data.repositories.MallRepository
import com.glass.data.repositories.ProductRepository
import com.glass.data.repositories.StoreRepository
import com.glass.domain.repositories.*
import com.glass.mouher.database.repositories.CartRepository
import org.koin.dsl.module.module

@Suppress("USELESS_CAST")
val DIRepositories = module {

    single{ MallRepository(
        api = get(DIConstants.APIs.MALL.name)
    ) as IMallRepository }

    single{ StoreRepository(
        api = get(DIConstants.APIs.STORE.name)
    ) as IStoreRepository }

    single{ ProductRepository(
        api = get(DIConstants.APIs.PRODUCT.name)
    ) as IProductRepository }

    single { CartRepository(
        realm = get(DIConstants.Externals.REALM.name)
    ) as ICartRepository }
}