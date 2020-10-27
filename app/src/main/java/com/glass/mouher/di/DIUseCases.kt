package com.glass.mouher.di

import com.glass.domain.usecases.mall.IMallUseCase
import com.glass.domain.usecases.mall.MallUseCase
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

    single(DIConstants.UseCases.USER_PROFILE.name) { UserUseCase(
        userProfileRepository = get()
    ) as IUserUseCase }

}