package com.glass.mouher.ui.registration.forgot

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.mouher.ui.base.BaseViewModel
import androidx.databinding.library.baseAdapters.BR

class ForgotPasswordViewModel(
    private val context: Context
): BaseViewModel() {

    @Bindable
    var backClicked: Unit? = null

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    fun onBackClicked(@Suppress("UNUSED_PARAMETER") v: View){
        notifyPropertyChanged(BR.backClicked)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}