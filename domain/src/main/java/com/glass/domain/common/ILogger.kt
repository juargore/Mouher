package com.glass.domain.common

/**
 * Plateforme specific logger and crash report interface.
 * To inject in each object which will require log or sending error reports.
 */
interface ILogger {
    /**
     * Log tool for debugging.
     */
    fun logDebug(tag: String, message: String?)


    /**
     * Log an error, should appears in crash reports.
     */
    fun logError(tag: String, message: String?)

    /**
     * Send the exception to the crash report tool.
     */
    fun logException(throwable: Throwable)
}