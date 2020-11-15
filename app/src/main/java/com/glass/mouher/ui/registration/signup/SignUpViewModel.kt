@file:Suppress("UNUSED_PARAMETER")

package com.glass.mouher.ui.registration.signup

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.mouher.ui.base.BaseViewModel
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.usecases.user.IUserUseCase

class SignUpViewModel(
    private val context: Context,
    private val userUseCase: IUserUseCase
): BaseViewModel() {

    @Bindable
    var backClicked: Unit? = null

    @Bindable
    var error: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.error)
        }

    @Bindable
    var fullName = ""

    @Bindable
    var fatherLastName = ""

    @Bindable
    var motherLastName = ""

    @Bindable
    var email = ""

    @Bindable
    var password = ""

    @Bindable
    var policyChecked = false
        set(value){
            field = value
            notifyPropertyChanged(BR.policyChecked)
        }

    @Bindable
    var conditionsChecked = false
        set(value){
            field = value
            notifyPropertyChanged(BR.conditionsChecked)
        }

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }



    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    fun onBackClicked(v: View){
        notifyPropertyChanged(BR.backClicked)
    }

    fun onSendClicked(v: View){
        if(allFieldsAreValid()){
            //TODO: Make it observable
            userUseCase.signUp()
        }
    }

    private fun allFieldsAreValid(): Boolean{
        if(fullName.isBlank()){
            error = "Por favor introduzca su nombre completo"
            return false
        }
        if(fatherLastName.isBlank()){
            error = "Por favor introduzca su apellido paterno"
            return false
        }
        if(motherLastName.isBlank()){
            error = "Por favor introduzca su apellido materno"
            return false
        }
        if(!isEmailValid()){
            error = "Por favor introduzca un email válido"
            return false
        }
        if(password.isBlank()){
            error = "Por favor introduzca una contraseña"
            return false
        }
        if(!policyChecked){
            error = "Debe aceptar la Política de Privacidad para continuar"
            return false
        }
        if(!conditionsChecked){
            error = "Debe aceptar los Términos y Condiciones para continuar"
            return false
        }
        return true
    }

    private fun isEmailValid(): Boolean{
        return email.isNotBlank() && "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex().matches(email)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}