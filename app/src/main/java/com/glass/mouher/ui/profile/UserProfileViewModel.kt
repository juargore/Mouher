package com.glass.mouher.ui.profile

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.domain.usecases.userProfile.IUserProfileUseCase
import com.glass.mouher.R
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.menu.AMenuViewModel
import com.glass.mouher.ui.menu.MenuItemViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserProfileViewModel(
    private val context: Context,
    private val userProfileUseCase: IUserProfileUseCase
): BaseViewModel() {

    @Bindable
    val openAddress: String? = null

    @Bindable
    val openPayment: String? = null

    @Bindable
    val userPhoto = R.drawable.face

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val disposable = userProfileUseCase.getUserProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponse, this::onError)

        addDisposable(disposable)
    }

    fun onEditAddressClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.openAddress)
    }

    fun onEditPaymentClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.openPayment)
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