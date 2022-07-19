@file:Suppress("UNUSED_PARAMETER")

package com.glass.mouher.ui.registration.forgot

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.ResponseUI
import com.glass.domain.usecases.user.IUserUseCase
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.extensions.isEmailValid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForgotPasswordViewModel(
    private val userUseCase: IUserUseCase
): BaseViewModel() {

    var hasError = true

    @Bindable
    var error: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.error)
        }

    @Bindable
    var userEmail = ""

    @Bindable
    var backClicked: Unit? = null

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    fun onBackClicked(@Suppress("UNUSED_PARAMETER") v: View){
        notifyPropertyChanged(BR.backClicked)
    }

    fun onSendClicked(v: View?){
        if (userEmail.isBlank() || !userEmail.trim().isEmailValid()) {
            error = "El correo electrónico es obligatorio."
        } else{
            progressVisible = true

            addDisposable(userUseCase.recoverPassword(userEmail.trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRecoverResponse, this::onError)
            )
        }
    }

    private fun onRecoverResponse(response: ResponseUI) {
        if (response.hasErrors) {
            hasError = true
            error = response.message
        } else {
            hasError = false
            error = "Correo enviado."
        }; progressVisible = false
    }

    private fun onError(t: Throwable?) {
        progressVisible = false
        hasError = true
        error = t?.message
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}