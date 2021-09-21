@file:Suppress("UNUSED_PARAMETER")

package com.glass.mouher.ui.profile.address

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Country
import com.glass.domain.entities.RegistrationData
import com.glass.domain.entities.State
import com.glass.domain.entities.UserProfileData
import com.glass.domain.usecases.user.IUserUseCase
import com.glass.mouher.shared.General.getUserId
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddressViewModel(
    private val userUseCase: IUserUseCase
): BaseViewModel() {

    private var isUpdatingAddress = false
    private var idAddress = 0
    var hasErrors = true

    @Bindable
    var error: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.error)
        }

    @Bindable
    var countriesList: List<Country> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.countriesList)
        }

    @Bindable
    var selectedCountry: Country? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.selectedCountry)
        }

    private var selectedCountryId: Int = 0

    @Bindable
    var statesList: List<State> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.statesList)
        }

    @Bindable
    var selectedState: State? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.selectedState)
        }


    private var selectedStateId: Int = 0

    @Bindable
    var municipality: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.municipality)
        }

    @Bindable
    var street: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.street)
        }

    @Bindable
    var extNumber: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.extNumber)
        }

    @Bindable
    var intNumber: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.intNumber)
        }

    @Bindable
    var betweenStreets: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.betweenStreets)
        }

    @Bindable
    var postalCode: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.postalCode)
        }

    @Bindable
    var suburb: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.suburb)
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
            .subscribe(this::onUserDataResponse, this::onError))
    }

    private fun onUserDataResponse(response: UserProfileData){
        isUpdatingAddress = !response.DomicilioEnvio.isNullOrEmpty()

        if(isUpdatingAddress){
            response.DomicilioEnvio?.get(0)?.let{ address->
                idAddress = address.IdDomicilio ?: 0

                with(address){
                    if(Pais != null){
                        selectedCountryId = Pais ?: 0

                        if(Estado != null){
                            selectedStateId = Estado ?: 0
                        }
                    }

                    municipality = Municipio
                    street = Calle
                    extNumber = NumExt
                    intNumber = NumInt
                    betweenStreets = Cruzamientos
                    postalCode = CP
                    suburb = Colonia
                }
            }
        }

        getCountriesList()
    }

    private fun getCountriesList(){
        statesList = listOf(State(0, "Estado *"))

        addDisposable(userUseCase.getCountriesAsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    selectedCountry = list.find { it.IdPais == selectedCountryId }
                    countriesList = list
                    progressVisible = false
                }, this::onError))
    }

    fun getStatesByCountryId(countryId: Int){
        addDisposable(userUseCase.getStatesAsList(countryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list->
                    selectedState = list.find { it.IdEstado == selectedStateId }
                    statesList = list
                    progressVisible = false
                }, this::onError)
        )
    }

    fun onSendClicked(v: View?){
        if(allFieldsValid()){
            progressVisible = true

            addDisposable(userUseCase.addOrUpdateAddress(
                isUpdatingAddress = isUpdatingAddress,
                id = if(isUpdatingAddress) idAddress else 0,
                userId = getUserId(),
                addressType = 1,
                street = street ?: "",
                intNumber = intNumber ?: "",
                extNumber = extNumber ?: "",
                crosses = betweenStreets ?: "",
                suburb = suburb ?: "",
                postalCode = postalCode ?: "",
                countryId = selectedCountry?.IdPais ?: 117,
                stateId = selectedState?.IdEstado ?: 14,
                municipality = municipality ?: ""
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSendResponse, this::onError)
            )
        }
    }

    private fun onSendResponse(response: RegistrationData){
        progressVisible = false

        if(response.Error!! > 0){
            hasErrors = true
            error = response.Mensaje
        }else{
            hasErrors = false
            error = response.Mensaje
        }
    }

    private fun allFieldsValid(): Boolean{
        if(selectedCountry == null || selectedCountry!!.IdPais == 0){
            error = "Seleccione un país de la lista."
            return false
        }
        if(selectedState == null || selectedState!!.IdEstado == 0){
            error = "Seleccione un Estado de la lista."
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