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
        REMOTE_CONFIG;

        override val identifier get() = String.format(diFormatId, DIKeys.Helper, this)
    }

    enum class Externals: IDependencyInjection {
        FIREBASE_STORAGE,
        FIREBASE_REMOTE_CONFIG,
        RETROFIT,
        LOGGER;

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
        MALL,
        STORE
        ;

        override val identifier get() = String.format(diFormatId, DIKeys.UseCase, this)
    }
}