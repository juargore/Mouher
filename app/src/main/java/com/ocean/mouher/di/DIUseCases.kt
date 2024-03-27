package com.ocean.mouher.di

import com.ocean.domain.usecases.cart.CartUseCase
import com.ocean.domain.usecases.cart.ICartUseCase
import com.ocean.domain.usecases.mall.IMallUseCase
import com.ocean.domain.usecases.mall.MallUseCase
import com.ocean.domain.usecases.product.IProductUseCase
import com.ocean.domain.usecases.product.ProductUseCase
import com.ocean.domain.usecases.store.IStoreUseCase
import com.ocean.domain.usecases.store.StoreUseCase
import com.ocean.domain.usecases.user.IUserUseCase
import com.ocean.domain.usecases.user.UserUseCase
import org.koin.dsl.module.module

@Suppress("USELESS_CAST")
val DIUseCases = module {

    single(DIConstants.UseCases.MALL.name) { MallUseCase(
        mallRepository = get()
    ) as IMallUseCase }

    single(DIConstants.UseCases.STORE.name) { StoreUseCase(
        storeRepository = get()
    ) as IStoreUseCase }

    single(DIConstants.UseCases.PRODUCT.name) { ProductUseCase(
        productRepository = get(),
        paymentRepository = get()
    ) as IProductUseCase }

    single(DIConstants.UseCases.USER.name) { UserUseCase(
        userRepository = get()
    ) as IUserUseCase }

    single(DIConstants.UseCases.CART.name) { CartUseCase(
        cartRepository = get()
    ) as ICartUseCase }

}