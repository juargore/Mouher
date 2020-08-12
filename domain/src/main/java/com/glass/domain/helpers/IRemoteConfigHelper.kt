package com.glass.domain.helpers

import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

/**
 * Config info, fetchde from firebase remote config.
 */
interface IRemoteConfigHelper {

    fun forceReload()

    /**
     * Try to initialize, and return true on success (or if already initialized).
     *
     * @return True if successfully initialized, as a Single
     */
    fun isInitialized(): Single<Boolean>

    fun getMinAppVersionObservable(): Observable<String>
}
