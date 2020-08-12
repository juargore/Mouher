package com.glass.mouher.di

import com.glass.data.repositories.firestore.*
import com.glass.domain.repositories.*
import org.koin.dsl.module.module

@Suppress("USELESS_CAST")
val DIRepositories = module {

    single{ CategoriesRepository(

    ) as ICategoriesRepository }


    factory{ ProductsRepository(

    ) as IProductsRepository }


    factory{ ProductDetailRepository(

    ) as IProductDetailRepository }


    single{ SocialNetworkRepository(

    ) as ISocialNetworkRepository }


    single{ UserListRepository(

    ) as IMenuRepository }


    single{ UserProfileRepository(

    ) as IUserProfileRepository }

    single{ OrdersRepository(

    ) as IOrderRepository }
}