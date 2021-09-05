@file:Suppress("UNUSED_PARAMETER")

package com.glass.mouher.ui.registration.signin

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.LoginData
import com.glass.domain.usecases.user.IUserUseCase
import com.glass.mouher.shared.General.saveUserEmail
import com.glass.mouher.shared.General.saveUserId
import com.glass.mouher.shared.General.saveUserName
import com.glass.mouher.shared.General.saveUserSignedIn
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.utils.isEmailValid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignInViewModel(
    private val userUseCase: IUserUseCase
): BaseViewModel() {

    var hasErrors = true

    @Bindable
    var passwordScreen: Unit? = null

    @Bindable
    var signupScreen: Unit? = null

    @Bindable
    var mainMallScreen: Unit? = null

    @Bindable
    var userEmail = ""

    @Bindable
    var userPassword = ""

    @Bindable
    var urlBackground = "https://i.redd.it/5apvhnd6wdh31.jpg"

    @Bindable
    var error: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.error)
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

    fun onPasswordClicked(v: View){
        notifyPropertyChanged(BR.passwordScreen)
    }

    fun onSignupClicked(v: View){
        notifyPropertyChanged(BR.signupScreen)
    }

    fun onSignInButtonClicked(v: View){
        if(allFieldsValid()){
            progressVisible = true
            addDisposable(userUseCase.signIn(userEmail.trim(), userPassword.trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSignInResponse, this::onError)
            )
        }
    }

    private fun onSignInResponse(response: LoginData){
        progressVisible = false

        if(response.Error!! > 0){
            hasErrors = true
            error = response.Mensaje
        }else{
            hasErrors = false
            error = response.Mensaje

            // save data on internal shared preferences
            with(response){
                val user = "$Nombre $ApellidoP $ApellidoM"
                val id = IdCliente ?: 0

                saveUserSignedIn(true)
                saveUserName(user)
                saveUserId(id)
                saveUserEmail(userEmail)
            }
        }
    }


    private fun onError(t: Throwable?){
        progressVisible = false
        hasErrors = true
        error = t?.message
    }

    fun onBackClicked(view: View){
        notifyPropertyChanged(BR.onBack)
    }

    private fun allFieldsValid(): Boolean{
        if(userEmail.isBlank() || !userEmail.trim().isEmailValid()){
            error = "Ingrese un email válido"
            return false
        }

        if(userPassword.isBlank()){
            error = "Ingrese una contraseña"
            return false
        }

        return true
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}