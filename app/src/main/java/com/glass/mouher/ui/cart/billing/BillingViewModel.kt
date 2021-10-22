@file:Suppress("UNUSED_PARAMETER")
package com.glass.mouher.ui.cart.billing

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.mouher.extensions.isEmailValid
import com.glass.mouher.ui.base.BaseViewModel

class BillingViewModel: BaseViewModel() {

    @Bindable
    var continueBilling: Unit? = null

    @Bindable
    var rfc: String = ""

    @Bindable
    var email: String = ""

    @Bindable
    var social: String = ""

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }

    @Bindable
    var error: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.error)
        }


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    fun onContinueButtonClicked(v: View?){
        if (rfc.isBlank() || email.isBlank() || social.isBlank()) {
            error = "Los datos mostrados son obligatorios."
        } else {
            if (!email.trim().isEmailValid()) {
                error = "El correo electrónico requiere un formato válido."
                return
            }
            notifyPropertyChanged(BR.continueBilling)
        }
    }

    fun onBackClicked(v: View?){
        notifyPropertyChanged(BR.onBack)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}