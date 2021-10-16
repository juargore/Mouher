package com.glass.mouher.di

import com.glass.domain.usecases.cart.CartUseCase
import com.glass.domain.usecases.cart.ICartUseCase
import com.glass.domain.usecases.mall.IMallUseCase
import com.glass.domain.usecases.mall.MallUseCase
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.domain.usecases.product.ProductUseCase
import com.glass.domain.usecases.store.IStoreUseCase
import com.glass.domain.usecases.store.StoreUseCase
import com.glass.domain.usecases.user.IUserUseCase
import com.glass.domain.usecases.user.UserUseCase
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