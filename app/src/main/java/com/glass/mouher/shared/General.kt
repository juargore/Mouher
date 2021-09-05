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

}