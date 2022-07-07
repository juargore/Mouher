package com.glass.mouher.shared

import android.content.Context
import com.glass.mouher.App.Companion.context

object General {

    private const val USER_SIGNED_IN = "userSignedIn"

    /**
     * Save and Get on SharedPreferences if user is signed in on App.
     * @param userSignedIn informs that user is signed in or not.
     * Used to check locally if User has been signed in previously.
     */
    fun saveUserSignedIn(userSignedIn: Boolean){
        context?.let{ c->
            with(c.getSharedPreferences(USER_SIGNED_IN, Context.MODE_PRIVATE)){
                edit().putBoolean(USER_SIGNED_IN, userSignedIn).apply()
            }
        }
    }

    fun getUserSignedIn() : Boolean {
        context?.let{ c->
            val prefs = c.getSharedPreferences(USER_SIGNED_IN, Context.MODE_PRIVATE)
            return prefs.getBoolean(USER_SIGNED_IN, false)
        }
        return false
    }


    private const val USER_NAME = "userName"

    /**
     * Save and Get on SharedPreferences the user name from Server.
     * @param userName is the one retrieved from Server.
     * Used on different screens as Profile to avoid a new call.
     */
    fun saveUserName(userName: String?){
        context?.let{ c->
            with(c.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)){
                edit().putString(USER_NAME, userName).apply()
            }
        }
    }

    fun getUserName() : String? {
        context?.let{ c->
            val prefs = c.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
            return prefs.getString(USER_NAME, "")
        }
        return ""
    }


    private const val USER_CREATION_DATE = "userCreationDate"

    /**
     * Save and Get on SharedPreferences the user name from Server.
     * @param date is the one retrieved from Server.
     * Used on different screens as Profile to avoid a new call.
     */
    fun saveUserCreationDate(date: String?){
        context?.let{ c->
            with(c.getSharedPreferences(USER_CREATION_DATE, Context.MODE_PRIVATE)){
                edit().putString(USER_CREATION_DATE, date).apply()
            }
        }
    }

    fun getUserCreationDate() : String? {
        context?.let{ c->
            val prefs = c.getSharedPreferences(USER_CREATION_DATE, Context.MODE_PRIVATE)
            return prefs.getString(USER_CREATION_DATE, "")
        }
        return ""
    }


    private const val USER_EMAIL = "userEmail"

    /**
     * Save and Get on SharedPreferences the user name from Server.
     * @param userName is the one retrieved from Server.
     * Used on different screens as Profile to avoid a new call.
     */
    fun saveUserEmail(userName: String?){
        context?.let{ c->
            with(c.getSharedPreferences(USER_EMAIL, Context.MODE_PRIVATE)){
                edit().putString(USER_EMAIL, userName).apply()
            }
        }
    }

    fun getUserEmail() : String? {
        context?.let{ c->
            val prefs = c.getSharedPreferences(USER_EMAIL, Context.MODE_PRIVATE)
            return prefs.getString(USER_EMAIL, "")
        }
        return ""
    }


    private const val USER_ID = "userId"

    /**
     * Save and Get on SharedPreferences the user name from Server.
     * @param userName is the one retrieved from Server.
     * Used on different screens as Profile to avoid a new call.
     */
    fun saveUserId(userName: Int){
        context?.let{ c->
            with(c.getSharedPreferences(USER_ID, Context.MODE_PRIVATE)){
                edit().putInt(USER_ID, userName).apply()
            }
        }
    }

    fun getUserId() : Int {
        context?.let{ c->
            val prefs = c.getSharedPreferences(USER_ID, Context.MODE_PRIVATE)
            return prefs.getInt(USER_ID, 0)
        }
        return 0
    }


    private const val MUST_REFRESH_MENU_MALL = "mustRefreshMenuMall"

    /**
     * Save and Get on SharedPreferences if user is signed in on App.
     * @param mustRefresh informs that user is signed in or not.
     * Used to check locally if User has been signed in previously.
     */
    fun saveMustRefreshMenuMall(mustRefresh: Boolean){
        context?.let{ c->
            with(c.getSharedPreferences(MUST_REFRESH_MENU_MALL, Context.MODE_PRIVATE)){
                edit().putBoolean(MUST_REFRESH_MENU_MALL, mustRefresh).apply()
            }
        }
    }

    fun getMustRefreshMenuMall() : Boolean {
        context?.let{ c->
            val prefs = c.getSharedPreferences(MUST_REFRESH_MENU_MALL, Context.MODE_PRIVATE)
            return prefs.getBoolean(MUST_REFRESH_MENU_MALL, false)
        }
        return false
    }


    private const val MUST_REFRESH_MENU_STORE = "mustRefreshMenuStore"

    /**
     * Save and Get on SharedPreferences if user is signed in on App.
     * @param mustRefresh informs that user is signed in or not.
     * Used to check locally if User has been signed in previously.
     */
    fun saveMustRefreshMenuStore(mustRefresh: Boolean){
        context?.let{ c->
            with(c.getSharedPreferences(MUST_REFRESH_MENU_STORE, Context.MODE_PRIVATE)){
                edit().putBoolean(MUST_REFRESH_MENU_STORE, mustRefresh).apply()
            }
        }
    }

    fun getMustRefreshMenuStore() : Boolean {
        context?.let{ c->
            val prefs = c.getSharedPreferences(MUST_REFRESH_MENU_STORE, Context.MODE_PRIVATE)
            return prefs.getBoolean(MUST_REFRESH_MENU_STORE, false)
        }
        return false
    }


    private const val CURRENT_STORE = "currentStore"

    /**
     * Save and Get on SharedPreferences the user name from Server.
     * @param storeName is the one retrieved from Server.
     * Used on different screens as Profile to avoid a new call.
     */
    fun saveCurrentStoreName(storeName: String?){
        context?.let{ c->
            with(c.getSharedPreferences(CURRENT_STORE, Context.MODE_PRIVATE)){
                edit().putString(CURRENT_STORE, storeName).apply()
            }
        }
    }

    fun getCurrentStoreName() : String? {
        context?.let{ c->
            val prefs = c.getSharedPreferences(CURRENT_STORE, Context.MODE_PRIVATE)
            return prefs.getString(CURRENT_STORE, "")
        }
        return ""
    }



    private const val CART_NOTES = "cartNotes"

    /**
     * Save and Get on SharedPreferences the user name from Server.
     * @param storeName is the one retrieved from Server.
     * Used on different screens as Profile to avoid a new call.
     */
    fun saveCartNotes(storeName: String?){
        context?.let{ c->
            with(c.getSharedPreferences(CART_NOTES, Context.MODE_PRIVATE)){
                edit().putString(CART_NOTES, storeName).apply()
            }
        }
    }

    fun getCartNotes() : String? {
        context?.let{ c->
            val prefs = c.getSharedPreferences(CART_NOTES, Context.MODE_PRIVATE)
            return prefs.getString(CART_NOTES, "")
        }
        return ""
    }


    private const val STORE_SHOPPING_INFO = "storeShoppingInfo"

    /**
     * Save and Get on SharedPreferences the user name from Server.
     * @param storeInfo is the one retrieved from Server.
     * Used on different screens as Profile to avoid a new call.
     */
    fun saveStoreShoppingInfo(storeInfo: String?){
        context?.let{ c->
            with(c.getSharedPreferences(STORE_SHOPPING_INFO, Context.MODE_PRIVATE)){
                edit().putString(STORE_SHOPPING_INFO, storeInfo).apply()
            }
        }
    }

    fun getStoreShoppingInfo() : String? {
        context?.let{ c->
            val prefs = c.getSharedPreferences(STORE_SHOPPING_INFO, Context.MODE_PRIVATE)
            return prefs.getString(STORE_SHOPPING_INFO, "0-0-0")
        }
        return ""
    }


    private const val FINAL_PAYMENT_INFO = "finalPaymentInfo"

    /**
     * Save and Get on SharedPreferences the user name from Server.
     * @param storeInfo is the one retrieved from Server.
     * Used on different screens as Profile to avoid a new call.
     */
    fun savePaymentInfo(storeInfo: String?){
        context?.let{ c->
            with(c.getSharedPreferences(FINAL_PAYMENT_INFO, Context.MODE_PRIVATE)){
                edit().putString(FINAL_PAYMENT_INFO, storeInfo).apply()
            }
        }
    }

    fun getPaymentInfo() : String {
        context?.let{ c->
            val prefs = c.getSharedPreferences(FINAL_PAYMENT_INFO, Context.MODE_PRIVATE)
            return prefs.getString(FINAL_PAYMENT_INFO, "false-0-0").toString()
        }
        return ""
    }


    private const val COMES_FROM_STORES = "comesFromStores"

    /**
     * Save and Get on SharedPreferences if user is signed in on App.
     * @param userSignedIn informs that user is signed in or not.
     * Used to check locally if User has been signed in previously.
     */
    fun saveComesFromStores(userSignedIn: Boolean){
        context?.let{ c->
            with(c.getSharedPreferences(COMES_FROM_STORES, Context.MODE_PRIVATE)){
                edit().putBoolean(COMES_FROM_STORES, userSignedIn).apply()
            }
        }
    }

    fun getComesFromStores() : Boolean {
        context?.let{ c->
            val prefs = c.getSharedPreferences(COMES_FROM_STORES, Context.MODE_PRIVATE)
            return prefs.getBoolean(COMES_FROM_STORES, false)
        }
        return false
    }


    private const val MUST_REFRESH_ACTIVITY_STORE = "mustRefreshActivityStore"

    /**
     * Save and Get on SharedPreferences if user is signed in on App.
     * @param userSignedIn informs that user is signed in or not.
     * Used to check locally if User has been signed in previously.
     */
    fun saveMustRefreshStore(userSignedIn: Boolean){
        context?.let{ c->
            with(c.getSharedPreferences(MUST_REFRESH_ACTIVITY_STORE, Context.MODE_PRIVATE)){
                edit().putBoolean(MUST_REFRESH_ACTIVITY_STORE, userSignedIn).apply()
            }
        }
    }

    fun getMusthRefreshStore() : Boolean {
        context?.let{ c->
            val prefs = c.getSharedPreferences(MUST_REFRESH_ACTIVITY_STORE, Context.MODE_PRIVATE)
            return prefs.getBoolean(MUST_REFRESH_ACTIVITY_STORE, false)
        }
        return false
    }


    private const val COMES_FROM_LOGIN = "comesFromLogin"

    fun saveComesFromLogin(comes: Boolean){
        context?.let{ c->
            with(c.getSharedPreferences(COMES_FROM_LOGIN, Context.MODE_PRIVATE)){
                edit().putBoolean(COMES_FROM_LOGIN, comes).apply()
            }
        }
    }

    fun getComesFromLogin() : Boolean {
        context?.let { c->
            val prefs = c.getSharedPreferences(COMES_FROM_LOGIN, Context.MODE_PRIVATE)
            return prefs.getBoolean(COMES_FROM_LOGIN, false)
        }
        return false
    }
}