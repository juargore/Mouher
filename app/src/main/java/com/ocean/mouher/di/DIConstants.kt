package com.ocean.mouher.di

object DIConstants {

    private const val diFormatId = "%s.%s"

    interface IDependencyInjection {
        val identifier: String
    }

    enum class DIKeys {
        External,
        Helper,
        Api,
        Repository,
        UseCase
    }


    /**
     *  Helpers
     */
    enum class Helpers: IDependencyInjection {
        REMOTE_CONFIG;

        override val identifier get() = String.format(diFormatId, DIKeys.Helper, this)
    }

    enum class Externals: IDependencyInjection {
        FIREBASE_STORAGE,
        FIREBASE_REMOTE_CONFIG,
        RETROFIT,
        REALM,
        LOGGER;

        override val identifier get() = String.format(diFormatId, DIKeys.External, this)
    }

    /**
     *  APIs
     */
    enum class APIs: IDependencyInjection {
        MALL,
        PAYMENT,
        PRODUCT,
        USER,
        STORE;

        override val identifier get() = String.format(diFormatId, DIKeys.Api, this)
    }

    /**
     *  Repositories
     */
    enum class Repositories: IDependencyInjection {
        MALL,
        STORE,
        PRODUCT
        ;

        override val identifier get() = String.format(diFormatId, DIKeys.Repository, this)
    }


    /**
     *  Use cases
     */
    enum class UseCases: IDependencyInjection {
        USER,
        MALL,
        STORE,
        PRODUCT,
        CART
        ;

        override val identifier get() = String.format(diFormatId, DIKeys.UseCase, this)
    }
}