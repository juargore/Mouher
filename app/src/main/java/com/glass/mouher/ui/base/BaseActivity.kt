package com.glass.mouher.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        Thread.setDefaultUncaughtExceptionHandler { _, paramThrowable ->
            Log.e("Error" + Thread.currentThread().stackTrace[2], paramThrowable.localizedMessage)
        }
    }
}