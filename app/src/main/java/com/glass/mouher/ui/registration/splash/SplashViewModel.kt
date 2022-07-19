@file:Suppress("DEPRECATION")

package com.glass.mouher.ui.registration.splash

import android.content.pm.PackageManager
import android.os.Handler
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.mouher.App.Companion.context
import com.glass.mouher.ui.base.BaseViewModel

class SplashViewModel: BaseViewModel() {

    @Bindable
    var nextScreen: Unit? = null

    @Bindable
    var versionStr: String? = null

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
        getCurrentVersionCode()

        Handler().postDelayed({
            notifyPropertyChanged(BR.nextScreen)
        }, 2000)
    }

    private fun getCurrentVersionCode(){
        context?.packageManager?.getPackageInfo(
            context!!.packageName,
            PackageManager.GET_ACTIVITIES)?.apply {
            versionStr = "versión $versionName"
        }
        notifyPropertyChanged(BR.versionStr)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}