@file:Suppress("UNUSED_PARAMETER")
package com.glass.mouher.ui.profile

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.glass.domain.common.or
import com.glass.domain.entities.UserProfileData
import com.glass.domain.usecases.cart.ICartUseCase
import com.glass.domain.usecases.user.IUserUseCase
import com.glass.mouher.shared.General
import com.glass.mouher.shared.General.getUserCreationDate
import com.glass.mouher.shared.General.getUserId
import com.glass.mouher.shared.General.getUserName
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.profile.address.AddressFragment
import com.glass.mouher.ui.profile.personal.PersonalFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserProfileViewModel(
    private val cartUseCase: ICartUseCase,
    private val userUseCase: IUserUseCase
): BaseViewModel() {

    var totalProductsOnDb: Int = 0

    @Bindable
    val signOut: Unit? = null


    /**
     * Variables for card personal info at top.
     */
    @Bindable
    var userName: String? = null

    @Bindable
    var memberSince: String? = null

    @Bindable
    var clientCode: String? = null

    @Bindable
    var userEmail: String? = null

    @Bindable
    var userPhone: String? = null

    @Bindable
    var userBirthDate: String? = null


    /**
     * Variables for card payment at middle.
     */
    @Bindable
    var cardNumber: String? = null

    @Bindable
    var cardOwner: String? = null

    @Bindable
    var cardVisible: Boolean = false


    /**
     * Variables for card address at bottom.
     */
    @Bindable
    var showEmptyAddress: Boolean = false

    @Bindable
    var addressStreet: String? = ""

    @Bindable
    var addressSuburb: String? = ""

    @Bindable
    var addressNumber: String? = ""

    @Bindable
    var addressState: String? = ""

    @Bindable
    var addressMunicipality: String? = ""

    @Bindable
    var addressCP: String? = ""

    @Bindable
    var addressCountry: String? = ""

    @Bindable
    var addressVisible: Boolean = true


    /**
     * Functions for buttons Edit or Delete on each card.
     */
    @Bindable
    var openProfileScreen: Fragment? = null

    @Bindable
    var addressButtonText = "MODIFICAR"


    /**
     * Show/hide progress on screen when api called.
     */
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
        userName = getUserName()
        notifyPropertyChanged(BR.userName)

        with(response){
            memberSince = "Miembro desde ${getUserCreationDate()}."
            clientCode = Codigo
            userEmail = "Correo Principal: $Correo"
            userPhone = TelMovil
            userBirthDate = "Fecha de Nacimiento: $FechaNac"

            notifyPropertyChanged(BR.userEmail)
            notifyPropertyChanged(BR.memberSince)
            notifyPropertyChanged(BR.clientCode)
            notifyPropertyChanged(BR.userPhone)
            notifyPropertyChanged(BR.userBirthDate)

            response.DomicilioEnvio?.get(0)?.let{ data->
                addressStreet = if(data.Calle.isNullOrEmpty()) "" else "Calle: ${data.Calle}."
                addressSuburb = if(data.Colonia.isNullOrEmpty()) "" else "Colonia: ${data.Colonia}"
                addressNumber = if(data.NumExt.isNullOrEmpty()) "" else "N° ${data.NumExt ?: ""}, Interior ${data.NumInt}."
                addressMunicipality = if(data.Municipio.isNullOrEmpty()) "" else "Ciudad: ${data.Municipio},"
                addressCP = if(data.CP.isNullOrEmpty()) "" else "Código Postal: ${data.CP}"

                notifyPropertyChanged(BR.addressStreet)
                notifyPropertyChanged(BR.addressSuburb)
                notifyPropertyChanged(BR.addressNumber)
                notifyPropertyChanged(BR.addressMunicipality)
                notifyPropertyChanged(BR.addressCP)

                if(addressStreet.isNullOrBlank()){
                    addressVisible = false
                    notifyPropertyChanged(BR.addressVisible)
                }

                showEmptyAddress = false
                notifyPropertyChanged(BR.showEmptyAddress)

                getCountries(data.Pais, data.Estado)
            }.or {
                showEmptyAddress = true
                addressStreet = ""
                addressButtonText = "Agregar"

                notifyPropertyChanged(BR.showEmptyAddress)
                notifyPropertyChanged(BR.addressStreet)
                notifyPropertyChanged(BR.addressButtonText)

                progressVisible = false
            }
        }
    }


    private fun getCountries(countryId: Int?, stateId: Int?){
        addDisposable(userUseCase.getCountriesAsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list->
                    val country = list.find { it.IdPais == countryId }?.Nombre
                    addressCountry = if(country.isNullOrEmpty()) "" else "País: $country"
                    notifyPropertyChanged(BR.addressCountry)

                    getStateByCountryId(countryId, stateId)
                }, this@UserProfileViewModel::onError))
    }


    private fun getStateByCountryId(countryId: Int?, stateId: Int?){
        addDisposable(userUseCase.getStatesAsList(countryId ?: 117)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list->
                    val state = list.find { it.IdEstado == stateId }?.Nombre
                    addressState = if(state.isNullOrEmpty()) "" else state
                    notifyPropertyChanged(BR.addressState)
                    progressVisible = false
                }, this::onError)
        )
    }


    fun onEditAddressClicked(v: View){
        openProfileScreen = AddressFragment()
        notifyPropertyChanged(BR.openProfileScreen)
    }


    fun onEditPersonalClicked(v: View){
        openProfileScreen = PersonalFragment()
        notifyPropertyChanged(BR.openProfileScreen)
    }


    fun clearProductsFromCart(){
        General.saveCartNotes("")
        cartUseCase.deleteAllProductsOnCart()
    }


    private fun onError(t: Throwable?){
        progressVisible = false
    }


    fun onBackClicked(v: View){
        notifyPropertyChanged(BR.backClicked)
    }

    fun onSignOutClicked(v: View?){
        addDisposable(cartUseCase.getSizeProductsOnDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ size->
                totalProductsOnDb = size
                notifyPropertyChanged(BR.signOut)
            }, this::onError)
        )
    }


    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}