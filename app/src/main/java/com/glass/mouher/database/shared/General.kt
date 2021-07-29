package com.glass.mouher.database.shared

import android.content.Context

object General {

    private const val MALL_ID = "mallId"

    fun saveCurrentMallId(context: Context, mallId: Int){
        val edit = context.getSharedPreferences(MALL_ID, Context.MODE_PRIVATE).edit()
        edit.putInt("cMallId", mallId)
        edit.apply()
    }

    fun getCurrentMallId(context: Context) : Int {
        val prefs = context.getSharedPreferences(MALL_ID, Context.MODE_PRIVATE)
        return prefs.getInt("cMallId", 0)
    }


    private const val STORE_ID = "storeId"

    fun saveCurrentStoreId(context: Context, storeId: Int){
        val edit = context.getSharedPreferences(STORE_ID, Context.MODE_PRIVATE).edit()
        edit.putInt("cStoreId", storeId)
        edit.apply()
    }

    fun getCurrentStoreId(context: Context) : Int {
        val prefs = context.getSharedPreferences(STORE_ID, Context.MODE_PRIVATE)
        return prefs.getInt("cStoreId", 0)
    }


    private const val USER_ID = "userId"

    fun saveCurrentUserId(context: Context, userId: Int){
        val edit = context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE).edit()
        edit.putInt("cUserId", userId)
        edit.apply()
    }

    fun getCurrentUserId(context: Context) : Int {
        val prefs = context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE)
        return prefs.getInt("cUserId", 0)
    }

}