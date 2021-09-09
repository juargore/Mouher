package com.glass.mouher.di

import com.glass.mouher.di.DIConstants.UseCases
import com.glass.mouher.ui.about.AboutViewModel
import com.glass.mouher.ui.cart.CartViewModel
import com.glass.mouher.ui.profile.address.AddressViewModel
import com.glass.mouher.ui.profile.payment.PaymentViewModel
import com.glass.mouher.ui.mall.home.HomeMallViewModel
import com.glass.mouher.ui.history.HistoryViewModel
import com.glass.mouher.ui.mall.home.stores.StoresViewModel
import com.glass.mouher.ui.menu.MenuViewModel
import com.glass.mouher.ui.profile.UserProfileViewModel
import com.glass.mouher.ui.profile.personal.PersonalViewModel
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
        userUseCase = get(UseCases.USER.name))
    }

    viewModel { SignUpViewModel(
        userUseCase = get(UseCases.USER.name))
    }

    viewModel { ForgotPasswordViewModel(
        userUseCase = get(UseCases.USER.name))
    }

    viewModel { HomeMallViewModel(
        mallUseCase = get(UseCases.MALL.name),
        storeUseCase = get(UseCases.STORE.name))
    }

    viewModel { MainStoreViewModel(
        cartUseCase = get(UseCases.CART.name),
        storeUseCase = get(UseCases.STORE.name))
    }

    viewModel { StoresViewModel(
        context = androidContext(),
        mallUseCase = get(UseCases.MALL.name))
    }

    viewModel { MenuViewModel(
        mallUseCase = get(UseCases.MALL.name),
        storeUseCase = get(UseCases.STORE.name))
    }

    viewModel { UserProfileViewModel(
        cartUseCase = get(UseCases.CART.name),
        userUseCase = get(UseCases.USER.name))
    }

    viewModel { HistoryViewModel(
        context = androidContext())
    }

    viewModel { HomeStoreViewModel(
        storeUseCase = get(UseCases.STORE.name),
        productUseCase = get(UseCases.PRODUCT.name),
        cartUseCase = get(UseCases.CART.name))
    }

    viewModel { ProductsViewModel(
        productUseCase = get(UseCases.PRODUCT.name))
    }

    viewModel { ProductDetailViewModel(
        cartUseCase = get(UseCases.CART.name),
        productUseCase = get(UseCases.PRODUCT.name))
    }

    viewModel { ProductReviewsViewModel(
        productUseCase = get(UseCases.PRODUCT.name))
    }

    viewModel { CartViewModel(
        cartUseCase = get(UseCases.CART.name))
    }

    viewModel { AddressViewModel(
        userUseCase = get(UseCases.USER.name))
    }

    viewModel { PaymentViewModel(
        context = androidContext())
    }

    viewModel { PersonalViewModel(
        context = androidContext(),
        userUseCase = get(UseCases.USER.name))
    }

    viewModel { AboutViewModel(
        mallUseCase = get(UseCases.MALL.name))
    }
}

