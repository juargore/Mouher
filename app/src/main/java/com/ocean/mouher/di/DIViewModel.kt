package com.ocean.mouher.di

import com.ocean.mouher.di.DIConstants.UseCases
import com.ocean.mouher.ui.about.AboutViewModel
import com.ocean.mouher.ui.cart.CartViewModel
import com.ocean.mouher.ui.cart.billing.BillingViewModel
import com.ocean.mouher.ui.history.HistoryViewModel
import com.ocean.mouher.ui.mall.home.HomeMallViewModel
import com.ocean.mouher.ui.mall.home.stores.StoresViewModel
import com.ocean.mouher.ui.menu.MenuViewModel
import com.ocean.mouher.ui.profile.UserProfileViewModel
import com.ocean.mouher.ui.profile.address.AddressViewModel
import com.ocean.mouher.ui.profile.payment.PaymentViewModel
import com.ocean.mouher.ui.profile.personal.PersonalViewModel
import com.ocean.mouher.ui.registration.forgot.ForgotPasswordViewModel
import com.ocean.mouher.ui.registration.signin.SignInViewModel
import com.ocean.mouher.ui.registration.signup.SignUpViewModel
import com.ocean.mouher.ui.registration.splash.SplashViewModel
import com.ocean.mouher.ui.search.SearchViewModel
import com.ocean.mouher.ui.store.MainStoreViewModel
import com.ocean.mouher.ui.store.home.HomeStoreViewModel
import com.ocean.mouher.ui.store.home.products.ProductsViewModel
import com.ocean.mouher.ui.store.home.products.proudctDetail.ProductDetailViewModel
import com.ocean.mouher.ui.store.home.products.proudctDetail.reviews.ProductReviewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val DIViewModel = module{

    viewModel { SplashViewModel() }

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
        mallUseCase = get(UseCases.MALL.name))
    }

    viewModel { MainStoreViewModel(
        cartUseCase = get(UseCases.CART.name),
        storeUseCase = get(UseCases.STORE.name))
    }

    viewModel { StoresViewModel(
        mallUseCase = get(UseCases.MALL.name))
    }

    viewModel { MenuViewModel(
        mallUseCase = get(UseCases.MALL.name),
        storeUseCase = get(UseCases.STORE.name),
        cartUseCase = get(UseCases.CART.name))
    }

    viewModel { UserProfileViewModel(
        cartUseCase = get(UseCases.CART.name),
        userUseCase = get(UseCases.USER.name))
    }

    viewModel { HistoryViewModel(
        productUseCase = get(UseCases.PRODUCT.name))
    }

    viewModel { HomeStoreViewModel(
        storeUseCase = get(UseCases.STORE.name),
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
        context = androidContext(),
        cartUseCase = get(UseCases.CART.name),
        productUseCase = get(UseCases.PRODUCT.name),
        userUseCase = get(UseCases.USER.name))
    }

    viewModel { SearchViewModel(
        productUseCase = get(UseCases.PRODUCT.name))
    }

    viewModel { BillingViewModel() }

    viewModel { com.ocean.mouher.ui.cart.payment.PaymentViewModel(
        productUseCase = get(UseCases.PRODUCT.name))
    }

    viewModel { AddressViewModel(
        userUseCase = get(UseCases.USER.name))
    }

    viewModel { PaymentViewModel() }

    viewModel { PersonalViewModel(
        userUseCase = get(UseCases.USER.name))
    }

    viewModel { AboutViewModel(
        mallUseCase = get(UseCases.MALL.name))
    }
}

