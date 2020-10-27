package com.glass.data.repositories.helpers

import com.glass.domain.common.ILogger
import com.glass.domain.helpers.IRemoteConfigHelper
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

/**
 * Remote config helper: instancied as an object to be ready before any intializations,
 * implements [IRemoteConfigHelper] to be easily used in Data package.
 */
class RemoteConfigHelper(
    private val remoteConfig: FirebaseRemoteConfig,
    private val logger: ILogger
): IRemoteConfigHelper {

    /**
     * All the parameters used by the app and stored in remote config.
     */
    private enum class Keys {
        privacyPolicy,
        termsAndConditions,
        androidMinimalVersion,
        UndeliverCode;
    }


    /**
     * @property isInitialized track if remote config has been successfully activated since app
     * start.
     */
    private var isInitialized = false

    override fun forceReload() {
        remoteConfig.fetch(0L)
            .addOnSuccessListener {
                activate().subscribe()
            }
            .addOnFailureListener {
                logger.logException(it)
            }
    }

    private val minAppVersionSubject = BehaviorSubject.create<String>()

    override fun getMinAppVersionObservable(): Observable<String> = minAppVersionSubject

    /**
     * @return true a configuration is correcly activated, false on errors, as a Single.
     */
    private fun activate(): Single<Boolean> {
        return Single.create { emitter ->
            remoteConfig.activate().addOnSuccessListener {
                isInitialized = true
                minAppVersionSubject.onNext(remoteConfig.getString(Keys.androidMinimalVersion.name))
                emitter.onSuccess(true)
            }.addOnFailureListener {
                logger.logException(it)
                emitter.onSuccess(false)
            }
        }
    }

    /**
     * @see [IRemoteConfigHelper.isInitializedObservable]
     */
    override fun isInitialized(): Single<Boolean> {
        if (isInitialized) {
            return Single.just(true)
        } else {
            return Single.create { emitter ->
                remoteConfig.fetch().addOnSuccessListener {
                    activate().subscribe { isNew ->
                        emitter.onSuccess(isNew)
                    }
                }.addOnFailureListener {
                    logger.logException(it)
                    emitter.onSuccess(false)
                }
            }
        }
    }
}
