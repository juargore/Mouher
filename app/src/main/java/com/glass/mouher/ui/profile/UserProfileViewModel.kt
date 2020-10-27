package com.glass.mouher.ui.profile

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.domain.usecases.user.IUserUseCase
import com.glass.mouher.R
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserProfileViewModel(
    private val context: Context
): BaseViewModel() {

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

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

}