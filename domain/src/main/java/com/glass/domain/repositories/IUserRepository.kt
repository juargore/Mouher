package com.glass.domain.repositories

interface IUserRepository {

    /**
     * Login the user.
     */
    fun login(user: String, pass: String)

    /**
     * Logout the user.
     */
    fun logout()
}