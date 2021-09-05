package com.glass.mouher.ui.profile

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.mouher.R
import com.glass.mouher.ui.base.BaseViewModel

class UserProfileViewModel: BaseViewModel() {

    @Bindable
    val signOut: Unit? = null

    @Bindable
    val openAddress: String? = null

    @Bindable
    val openPayment: String? = null

    @Bindable
    var onDiscard: String? = null

    @Bindable
    val userPhoto = R.drawable.face

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    fun onEditAddressClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.openAddress)
    }

    fun onEditPaymentClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.openPayment)
    }

    fun onDiscardPaymentClicked(@Suppress("UNUSED_PARAMETER") view: View){
        onDiscard = "PAYMENT"
        notifyPropertyChanged(BR.onDiscard)
    }

    fun onDiscardAddressClicked(@Suppress("UNUSED_PARAMETER") view: View){
        onDiscard = "ADDRESS"
        notifyPropertyChanged(BR.onDiscard)
    }

    private fun onResponse(user: Item){

    }

    private fun onError(t: Throwable?){

    }

    fun onSignOutClicked(v: View?){
        notifyPropertyChanged(BR.signOut)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

}