package com.glass.mouher.di

import com.glass.domain.usecases.IUITabBarUseCase
import com.glass.domain.usecases.UITabBarUseCase
import com.glass.domain.usecases.categories.CategoriesUsecase
import com.glass.domain.usecases.categories.ICategoriesUseCase
import com.glass.domain.usecases.history.IHistoryUseCase
import com.glass.domain.usecases.history.HistoryUseCase
import com.glass.domain.usecases.productDetail.IProductDetailUseCase
import com.glass.domain.usecases.productDetail.ProductDetailUseCase
import com.glass.domain.usecases.products.IProductsUseCase
import com.glass.domain.usecases.products.ProductsUseCase
import com.glass.domain.usecases.socialNetwork.ISocialNetworkUseCase
import com.glass.domain.usecases.socialNetwork.SocialNetworkUseCase
import com.glass.domain.usecases.userList.IMenuUseCase
import com.glass.domain.usecases.userList.MenuUseCase
import com.glass.domain.usecases.userList.detail.IUserListDetailUseCase
import com.glass.domain.usecases.userList.detail.UserListDetailUseCase
import com.glass.domain.usecases.userProfile.IUserProfileUseCase
import com.glass.domain.usecases.userProfile.UserProfileUseCase
import org.koin.dsl.module.module

@Suppress("USELESS_CAST")
val DIUseCases = module {

    single(DIConstants.UseCases.CATEGORIES.name) { CategoriesUsecase(
        categoriesRepository = get()
    ) as ICategoriesUseCase }

    factory(DIConstants.UseCases.PRODUCTS.name) { ProductsUseCase(
        productsRepository = get()
    ) as IProductsUseCase }

    factory(DIConstants.UseCases.PRODUCT_DETAIL.name) { ProductDetailUseCase(
        productDetailRepository = get()
    ) as IProductDetailUseCase }

    single(DIConstants.UseCases.SOCIAL_NETWORK.name) { SocialNetworkUseCase(
        socialNetworkRepository = get()
    ) as ISocialNetworkUseCase }

    single(DIConstants.UseCases.USER_LIST.name) { MenuUseCase(
        userListRepository = get()
    ) as IMenuUseCase }

    single(DIConstants.UseCases.USER_LIST_DETAIL.name) { UserListDetailUseCase(
        userListRepository = get()
    ) as IUserListDetailUseCase }

    single(DIConstants.UseCases.USER_PROFILE.name) { UserProfileUseCase(
        userProfileRepository = get()
    ) as IUserProfileUseCase }

    single(DIConstants.UseCases.ORDERS.name) { HistoryUseCase(
        ordersRepository = get()
    ) as IHistoryUseCase }

    single(DIConstants.UseCases.TAB_BAR.name) { UITabBarUseCase(

    ) as IUITabBarUseCase }

}