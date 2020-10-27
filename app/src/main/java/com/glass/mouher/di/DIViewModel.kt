package com.glass.mouher.di

import com.glass.mouher.di.DIConstants.UseCases
import com.glass.mouher.ui.cart.CartViewModel
import com.glass.mouher.ui.checkout.address.AddressViewModel
import com.glass.mouher.ui.checkout.payment.PaymentViewModel
import com.glass.mouher.ui.mall.home.HomeMallViewModel
import com.glass.mouher.ui.history.HistoryViewModel
import com.glass.mouher.ui.mall.home.stores.StoresViewModel
import com.glass.mouher.ui.menu.MenuViewModel
import com.glass.mouher.ui.profile.UserProfileViewModel
import com.glass.mouher.ui.registration.forgot.ForgotPasswordViewModel
import com.glass.mouher.ui.registration.signin.SignInViewModel
import com.glass.mouher.ui.registration.signup.SignUpViewModel
import com.glass.mouher.ui.registration.splash.SplashViewModel
import com.glass.mouher.ui.store.MainStoreViewModel
import com.glass.mouher.ui.store.home.HomeStoreViewModel
import com.glass.mouher.ui.store.home.products.ProductsViewModel
import com.glass.mouher.ui.store.home.products.proudctDetail.ProductDetailViewModel
import com.glass.mouher.ui.store.home.products.proudctDetail.reviews.ProductReviewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val DIViewModel = module{

    viewModel { SplashViewModel(
        context = androidContext())
    }

    viewModel { SignInViewModel(
        context = androidContext())
    }

    viewModel { SignUpViewModel(
        context = androidContext())
    }

    viewModel { ForgotPasswordViewModel(
        context = androidContext())
    }

    viewModel { HomeMallViewModel(
        context = androidContext(),
        mallUseCase = get(UseCases.MALL.name),
        storeUseCase = get(UseCases.STORE.name))
    }

    viewModel { MainStoreViewModel(
        context = androidContext())
    }

    viewModel { StoresViewModel(
        context = androidContext(),
        mallUseCase = get(UseCases.MALL.name))
    }

    viewModel { MenuViewModel(
        context = androidContext(),
        mallUseCase = get(UseCases.MALL.name))
    }

    viewModel { UserProfileViewModel(
        context = androidContext())
    }

    viewModel { HistoryViewModel(
        context = androidContext())
    }

    viewModel { HomeStoreViewModel(
        context = androidContext())
    }

    viewModel { ProductsViewModel(
        context = androidContext())
    }

    viewModel { ProductDetailViewModel(
        context = androidContext())
    }

    viewModel { ProductReviewsViewModel(
        context = androidContext())
    }

    viewModel { CartViewModel(
        context = androidContext())
    }

    viewModel { AddressViewModel(
        context = androidContext())
    }

    viewModel { PaymentViewModel(
        context = androidContext())
    }
}

