package com.glass.domain.common

import java.lang.Exception

class NoConnectivityException : Exception() {

    companion object {
        const val message = "Zipline : internet not found"
    }

    override val message: String
        get() = NoConnectivityException.message
}