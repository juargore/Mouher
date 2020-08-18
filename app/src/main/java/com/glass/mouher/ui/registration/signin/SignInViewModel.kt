package com.glass.mouher.ui.registration.signin

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.mouher.ui.base.BaseViewModel
import androidx.databinding.library.baseAdapters.BR

class SignInViewModel(
    private val context: Context
): BaseViewModel() {

    @Bindable
    var passwordScreen: Unit? = null

    @Bindable
    var signupScreen: Unit? = null

    @Bindable
    var mainMallScreen: Unit? = null

    @Bindable
    var urlBackground = "https://i.redd.it/5apvhnd6wdh31.jpg"

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    fun onPasswordClicked(@Suppress("UNUSED_PARAMETER") v: View){
        notifyPropertyChanged(BR.passwordScreen)
    }

    fun onSignupClicked(@Suppress("UNUSED_PARAMETER") v: View){
        notifyPropertyChanged(BR.signupScreen)
    }

    fun onSignInButtonClicked(@Suppress("UNUSED_PARAMETER") v: View){
        notifyPropertyChanged(BR.mainMallScreen)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}