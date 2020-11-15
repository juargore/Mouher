package com.glass.mouher.di

import com.glass.mouher.di.DIConstants.UseCases
import com.glass.mouher.ui.about.AboutViewModel
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
        context = androidContext(),
        userUseCase = get(UseCases.USER_PROFILE.name))
    }

    viewModel { SignUpViewModel(
        context = androidContext(),
        userUseCase = get(UseCases.USER_PROFILE.name))
    }

    viewModel { ForgotPasswordViewModel(
        context = androidContext(),
        userUseCase = get(UseCases.USER_PROFILE.name))
    }

    viewModel { HomeMallViewModel(
        context = androidContext(),
        mallUseCase = get(UseCases.MALL.name),
        storeUseCase = get(UseCases.STORE.name))
    }

    viewModel { MainStoreViewModel(
        context = androidContext(),
        cartUseCase = get(UseCases.CART.name),
        storeUseCase = get(UseCases.STORE.name))
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
        context = androidContext(),
        storeUseCase = get(UseCases.STORE.name),
        productUseCase = get(UseCases.PRODUCT.name)
    )
    }

    viewModel { ProductsViewModel(
        context = androidContext(),
        productUseCase = get(UseCases.PRODUCT.name))
    }

    viewModel { ProductDetailViewModel(
        context = androidContext(),
        cartUseCase = get(UseCases.CART.name),
        productUseCase = get(UseCases.PRODUCT.name))
    }

    viewModel { ProductReviewsViewModel(
        context = androidContext(),
        productUseCase = get(UseCases.PRODUCT.name))
    }

    viewModel { CartViewModel(
        context = androidContext(),
        cartUseCase = get(UseCases.CART.name))
    }

    viewModel { AddressViewModel(
        context = androidContext())
    }

    viewModel { PaymentViewModel(
        context = androidContext())
    }

    viewModel { AboutViewModel(
            context = androidContext(),
            mallUseCase = get(UseCases.MALL.name))
    }
}

