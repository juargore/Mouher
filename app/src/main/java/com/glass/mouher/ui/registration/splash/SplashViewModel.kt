package com.glass.mouher.ui.registration.splash

import android.content.Context
import android.os.Handler
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.mouher.ui.base.BaseViewModel
import androidx.databinding.library.baseAdapters.BR

class SplashViewModel(
    private val context: Context
): BaseViewModel() {

    @Bindable
    var nextScreen: Unit? = null

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        Handler().postDelayed({
            notifyPropertyChanged(BR.nextScreen)
        }, 2000)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}