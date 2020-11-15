@file:Suppress("UNUSED_PARAMETER")

package com.glass.mouher.ui.registration.forgot

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.mouher.ui.base.BaseViewModel
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.usecases.user.IUserUseCase

class ForgotPasswordViewModel(
    private val context: Context,
    private val userUseCase: IUserUseCase
): BaseViewModel() {

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
        if(!isEmailValid()){
            error = "Introduzca su correo electrónico válido"
        } else{
            //TODO: Make it observable
            userUseCase.recoverPassword()
        }
    }

    private fun isEmailValid(): Boolean{
        return userEmail.isNotBlank() && "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex().matches(userEmail)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}