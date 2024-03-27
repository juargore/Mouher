package com.ocean.mouher.di

import com.ocean.data.repositories.*
import com.ocean.domain.repositories.*
import com.ocean.mouher.database.repositories.CartRepository
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

    single { UserRepository(
        api = get(DIConstants.APIs.USER.name)
    ) as IUserRepository }

    single{ PaymentRepository(
        api = get(DIConstants.APIs.PAYMENT.name)
    ) as IPaymentRepository }
}