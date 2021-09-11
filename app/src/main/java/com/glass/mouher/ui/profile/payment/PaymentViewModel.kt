package com.glass.mouher.ui.profile.payment

import androidx.databinding.Observable
import com.glass.mouher.ui.base.BaseViewModel

class PaymentViewModel: BaseViewModel() {

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}