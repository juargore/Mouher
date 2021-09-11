@file:Suppress("UNUSED_PARAMETER")

package com.glass.mouher.ui.registration.signup

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.RegistrationData
import com.glass.domain.usecases.user.IUserUseCase
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.utils.WebBrowserUtils.openUrlInExternalWebBrowser
import com.glass.mouher.extensions.isEmailValid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class SignUpViewModel(
    private val userUseCase: IUserUseCase
): BaseViewModel() {

    @Bindable
    var backClicked: Unit? = null

    var hasErrors = true

    @Bindable
    var error: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.error)
        }


    @Bindable
    var fullName: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.fullName)
        }


    @Bindable
    var fatherLastName: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.fatherLastName)
        }


    @Bindable
    var motherLastName: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.motherLastName)
        }


    @Bindable
    var gender = 0 // 1 -> male && 2 -> female
        set(value){
            field = value
            notifyPropertyChanged(BR.gender)
        }

    @Bindable
    var genderList: List<String> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.genderList)
        }

    @Bindable
    var birthDate: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.birthDate)
        }


    @Bindable
    var birthDateStr = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.birthDateStr)
        }


    var day: Int = 0; var month: Int = 0; var year: Int = 0


    @Bindable
    var birthDateClicked: Unit? = null


    @Bindable
    var phone = ""
        set(value){
            field = value
            notifyPropertyChanged(BR.phone)
        }


    @Bindable
    var email = ""
        set(value){
            field = value
            notifyPropertyChanged(BR.email)
        }


    @Bindable
    var passwordOne: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.passwordOne)
        }

    @Bindable
    var passwordTwo: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.passwordTwo)
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
        genderList = userUseCase.getGenderList()
    }

    fun onBackClicked(v: View){
        notifyPropertyChanged(BR.backClicked)
    }

    fun onSendClicked(v: View){
        if(allFieldsAreValid()){
            progressVisible = true

            addDisposable(userUseCase.signUp(
                name = fullName?.trim() ?: "",
                fLastName = fatherLastName?.trim() ?: "",
                mLastName = motherLastName?.trim() ?: "",
                gender = gender,
                birthday = birthDate?.trim() ?: "0000-00-00",
                phone = phone.trim(),
                email = email.trim(),
                password = passwordOne?.trim() ?: ""
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRegistrationResponse, this::onError)
            )
        }
    }

    private fun onRegistrationResponse(response: RegistrationData){
        progressVisible = false

        if(response.Error!! > 0){
            hasErrors = true
            error = response.Mensaje
        }else{
            // No errors -> Show success message
            hasErrors = false
            error = response.Mensaje
        }
    }


    /** Function to notify view that must display the datepicker for birth date selection. */
    fun onBirthDayClicked(v: View?){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)

        notifyPropertyChanged(BR.birthDateClicked)
    }


    private fun allFieldsAreValid(): Boolean{
        if(fullName.isNullOrBlank()){
            error = "Por favor ingrese su nombre"
            return false
        }
        if(fatherLastName.isNullOrBlank()){
            error = "Por favor ingrese su apellido paterno"
            return false
        }
        if(motherLastName.isNullOrBlank()){
            error = "Por favor ingrese su apellido materno"
            return false
        }
        if(gender == 0){
            error = "Por favor seleccione un género de la lista"
            return false
        }
        if(birthDate.isNullOrBlank()){
            error = "Por favor ingrese su fecha de nacimiento"
            return false
        }
        if(phone.isBlank() || phone.length < 10){
            error = "Por favor ingrese un número de teléfono de al menos 10 dígitos"
            return false
        }
        if(!email.isEmailValid()){
            error = "Por favor ingrese un email válido"
            return false
        }
        if(passwordOne.isNullOrBlank()){
            error = "Por favor ingrese una contraseña"
            return false
        }
        if(passwordTwo.isNullOrBlank()){
            error = "Por favor confirme la contraseña"
            return false
        }
        if(passwordOne != passwordTwo){
            error = "Las contraseñas ingresadas no coinciden"
            return false
        }
        if(!conditionsChecked){
            error = "Debe aceptar los Términos y Condiciones para continuar"
            return false
        }
        return true
    }

    fun onTermsClicked(v: View?){
        val url = "https://mouhermarket.com/admin/uploads/tienda0/TD-ID0-CPLA-ID1-PoliticaCondiciones.PDF"
        openUrlInExternalWebBrowser(url)
    }

    private fun onError(t: Throwable){
        progressVisible = false
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}