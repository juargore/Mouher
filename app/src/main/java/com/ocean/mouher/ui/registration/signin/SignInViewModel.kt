@file:Suppress("UNUSED_PARAMETER")

package com.ocean.mouher.ui.registration.signin

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.ocean.domain.entities.LoginData
import com.ocean.domain.usecases.user.IUserUseCase
import com.ocean.mouher.shared.General.saveUserEmail
import com.ocean.mouher.shared.General.saveUserId
import com.ocean.mouher.shared.General.saveUserName
import com.ocean.mouher.shared.General.saveUserSignedIn
import com.ocean.mouher.ui.base.BaseViewModel
import com.ocean.mouher.extensions.isEmailValid
import com.ocean.mouher.shared.General.saveUserCreationDate
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

    fun onPasswordClicked(v: View?){
        notifyPropertyChanged(BR.passwordScreen)
    }

    fun onSignupClicked(v: View?){
        notifyPropertyChanged(BR.signupScreen)
    }

    fun onSignInButtonClicked(v: View?) {
        if (allFieldsValid()) {
            progressVisible = true
            addDisposable(userUseCase.signIn(userEmail.trim(), userPassword.trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSignInResponse, this::onError)
            )
        }
    }

    private fun onSignInResponse(response: LoginData) {
        progressVisible = false

        if (response.Error > 0) {
            hasErrors = true
            error = response.Mensaje
        } else {
            hasErrors = false
            error = "¡Bienvenid@ a Mouher Market!"

            // save data on internal shared preferences
            with(response) {
                val user = "$Nombre $ApellidoP $ApellidoM"
                val id = IdCliente ?: 0

                saveUserSignedIn(true)
                saveUserName(user)
                saveUserId(id)
                saveUserEmail(userEmail)
                saveUserCreationDate(response.FechaAlta)
            }
        }
    }

    private fun onError(t: Throwable?) {
        progressVisible = false
        hasErrors = true
        error = t?.message
    }

    private fun allFieldsValid(): Boolean {
        if (userEmail.isBlank() || !userEmail.trim().isEmailValid()) {
            error = "El correo es obligatorio."
            return false
        }

        if (userPassword.isBlank()) {
            error = "La contraseña es obligatoria."
            return false
        }

        return true
    }

    fun onBackClicked(view: View?) {
        notifyPropertyChanged(BR.onBack)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}