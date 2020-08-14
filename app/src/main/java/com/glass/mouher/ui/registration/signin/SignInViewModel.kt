package com.glass.mouher.ui.registration.signin

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.mouher.ui.base.BaseViewModel

class SignInViewModel(
    private val context: Context
): BaseViewModel() {

    @Bindable
    var urlBackground = "https://i.redd.it/5apvhnd6wdh31.jpg"

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}