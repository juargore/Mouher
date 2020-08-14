package com.glass.mouher.di

import com.glass.mouher.di.DIConstants.UseCases
import com.glass.mouher.ui.mall.MainViewModel
import com.glass.mouher.ui.mall.home.HomeViewModel
import com.glass.mouher.ui.history.HistoryViewModel
import com.glass.mouher.ui.mall.home.stores.StoresViewModel
import com.glass.mouher.ui.menu.MenuViewModel
import com.glass.mouher.ui.profile.UserProfileViewModel
import com.glass.mouher.ui.registration.signin.SignInViewModel
import com.glass.mouher.ui.store.MainViewModelStore
import com.glass.mouher.ui.store.home.HomeStoreViewModel
import com.glass.mouher.ui.store.home.products.ProductsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val DIViewModel = module{

    viewModel {
        MainViewModel(
            tabBarUseCase = get(UseCases.TAB_BAR.name),
            context = androidContext()
        )
    }

    viewModel {
        MainViewModelStore(
            tabBarUseCase = get(UseCases.TAB_BAR.name),
            context = androidContext()
        )
    }

    viewModel { HomeViewModel(
        context = androidContext(),
        categoriesUseCase = get(UseCases.CATEGORIES.name))
    }

    viewModel { StoresViewModel(
        context = androidContext())
    }

    viewModel { MenuViewModel(
        context = androidContext(),
        menuUseCase = get(UseCases.USER_LIST.name))
    }

    viewModel { UserProfileViewModel(
        context = androidContext(),
        userProfileUseCase = get(UseCases.USER_PROFILE.name))
    }

    viewModel { HistoryViewModel(
        context = androidContext(),
        orderUseCase = get(UseCases.ORDERS.name))
    }

    viewModel { HomeStoreViewModel(
        context = androidContext())
    }

    viewModel { ProductsViewModel(
        context = androidContext())
    }

    viewModel { SignInViewModel(
        context = androidContext())
    }
}

