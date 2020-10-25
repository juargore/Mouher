package com.glass.mouher.di

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
        CURRENT_USER,
        REMOTE_CONFIG;

        override val identifier get() = String.format(diFormatId, DIKeys.Helper, this)
    }

    enum class Externals: IDependencyInjection {
        FIREBASE_AUTHENTICATION,
        FIREBASE_FIRESTORE,
        FIREBASE_STORAGE,
        FIREBASE_STORAGE_LEGAL,
        FIREBASE_REMOTE_CONFIG,
        RETROFIT,
        LOGGER,
        REALM;

        override val identifier get() = String.format(diFormatId, DIKeys.External, this)
    }

    /**
     *  APIs
     */
    enum class APIs: IDependencyInjection {
        MALL,
        STORE;

        override val identifier get() = String.format(diFormatId, DIKeys.Api, this)
    }

    /**
     *  Repositories
     */
    enum class Repositories: IDependencyInjection {
        USER_PROFILE,
        CATEGORIES,
        PRODUCTS,
        PRODUCT_DETAIL,
        FAQ,
        SOCIAL_NETWORK,
        USER_LIST,
        USER_LIST_DETAIL,
        ORDERS,
        TAB_BAR,
        MALL,
        STORE
        ;

        override val identifier get() = String.format(diFormatId, DIKeys.Repository, this)
    }


    /**
     *  Use cases
     */
    enum class UseCases: IDependencyInjection {
        USER_PROFILE,
        CATEGORIES,
        PRODUCTS,
        PRODUCT_DETAIL,
        FAQ,
        SOCIAL_NETWORK,
        USER_LIST,
        USER_LIST_DETAIL,
        ORDERS,
        TAB_BAR,
        MALL,
        STORE
        ;

        override val identifier get() = String.format(diFormatId, DIKeys.UseCase, this)
    }
}