package com.ocean.domain.common

import java.lang.Exception

class NoConnectivityException : Exception() {

    companion object {
        const val message = "Mouher : internet not found"
    }

    override val message: String
        get() = NoConnectivityException.message
}