@file:Suppress("DEPRECATION", "UNUSED_PARAMETER")

package com.glass.mouher.ui.profile.personal

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.UserProfileData
import com.glass.domain.usecases.user.IUserUseCase
import com.glass.mouher.App.Companion.context
import com.glass.mouher.shared.General.getUserId
import com.glass.mouher.shared.General.saveUserName
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.utils.Validations
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class PersonalViewModel(
    private val userUseCase: IUserUseCase
): BaseViewModel() {

    var hasErrors = true
    private var userId = 0
    private var userCode = ""
    private var storedPassword: String = ""

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
    var gender = 0
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
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        progressVisible = true

        addDisposable(userUseCase.getUserData(getUserId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onUserDataResponse, this::onError)
        )
    }

    private fun onUserDataResponse(response: UserProfileData){
        // From  2020-11-21  to  21-11-2020
        val _year = response.FechaNac?.substringBefore("-")
        val _month = response.FechaNac?.substringAfter("-")?.substringBeforeLast("-")
        val _day = response.FechaNac?.substringAfterLast("-")
        val birthDateFormatted = "$_day-$_month-$_year"

        day = _day?.toInt() ?: 1
        month = _month?.toInt() ?: 1
        year = _year?.toInt() ?: 2020

        with(response){
            userId = Id ?: 0
            userCode = Codigo ?: ""
            fullName = Nombre
            fatherLastName = ApellidoP
            motherLastName = ApellidoM
            birthDateStr = Validations.toPrettyDate(context, birthDateFormatted, Locale("es"))
            birthDate = FechaNac
            phone = TelMovil ?: ""
            gender = Genero ?: 0
            email = Correo ?: ""
            storedPassword = Contrasena ?: ""
            passwordOne = Contrasena ?: ""
            passwordTwo = Contrasena ?: ""
        }

        genderList = userUseCase.getGenderList()
        progressVisible = false
    }


    /** Function to notify view that must display the datepicker for birth date selection. */
    fun onBirthDayClicked(v: View?){
        notifyPropertyChanged(BR.birthDateClicked)
    }

    fun onUpdateButtonClicked(v: View?){
        if(allFieldsAreValid()){
            progressVisible = true

            addDisposable(userUseCase.updateUser(
                id = userId,
                code = userCode,
                name = fullName ?: "",
                fLastName = fatherLastName ?: "",
                mLastName = motherLastName ?: "",
                gender = gender,
                birthday = birthDate ?: "0000-00-00",
                phone = phone,
                email = email,
                password = passwordOne ?: ""
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUpdatedDataResponse, this::onError)
            )
        }
    }

    private fun onUpdatedDataResponse(response: RegistrationData){
        progressVisible = false

        if(response.Error!! > 0){
            // something went wrong
            hasErrors = true
            error = response.Mensaje
        }else{
            // Update on menu
            val user = "$fullName $fatherLastName $motherLastName"
            saveUserName(user)

            // everything goes well
            hasErrors = false
            error = response.Mensaje
        }
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
        if(phone.isBlank()){
            error = "Por favor ingrese un número de teléfono"
            return false
        }
        if(passwordOne.isNullOrBlank()){
            error = "Por favor ingrese una contraseña"
            return false
        }
        if(passwordTwo.isNullOrBlank()){
            error = "Por favor ingrese la confirmación de contraseña"
            return false
        }
        if(passwordOne != passwordTwo){
            error = "Las contraseñas no coinciden"
            return false
        }
        return true
    }

    fun onBackClicked(v: View){
        notifyPropertyChanged(BR.backClicked)
    }

    private fun onError(t: Throwable?){
        progressVisible = false
        hasErrors = true
        error = t?.message
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}