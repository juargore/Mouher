package com.glass.mouher.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.glass.mouher.R

fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        statusBarColor = Color.TRANSPARENT
    }
}

fun Activity.hideKeyboard(){
    val view = this.findViewById<ViewGroup>(android.R.id.content)
    view?.let {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun FragmentActivity.openOrRefreshFragment(forStore: Boolean, destination: Fragment?, args: Bundle?, name: String?){
    destination?.let{
        supportFragmentManager.beginTransaction().apply {
            replace(if(forStore) R.id.container_body else R.id.container_body_mall, destination.apply {
                args?.let{
                    arguments = args
                }
            })

            name?.let{
                addToBackStack(it)
            }

            commit()
        }
    }
}

fun Fragment.startActivityNoAnimation(intent: Intent?, finishActivity: Boolean = false) {
    activity?.overridePendingTransition(0,0)

    intent?.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
    startActivity(intent)

    if (finishActivity) {
        activity?.finish()
    }
}

fun AppCompatActivity.startActivityNoAnimation(intent: Intent?, finishActivity: Boolean = false){
    overridePendingTransition(0,0)
    intent?.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
    startActivity(intent)

    if (finishActivity) {
       finish()
    }
}
