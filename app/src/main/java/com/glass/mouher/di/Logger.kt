package com.glass.mouher.di

import android.util.Log
import com.glass.domain.common.ILogger
import com.glass.mouher.BuildConfig


class Logger private constructor(): ILogger {

    private object HOLDER {
        val INSTANCE = Logger()
    }

    companion object {
        val instance: Logger by lazy { HOLDER.INSTANCE }
    }

    override fun logDebug(tag: String, message: String?) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message ?: "null")
        }
    }

    override fun logError(tag: String, message: String?) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message ?: "")
        }
    }

    override fun logException(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e("ExceptionToCrashlytics", throwable.message, throwable)
        }
    }
}