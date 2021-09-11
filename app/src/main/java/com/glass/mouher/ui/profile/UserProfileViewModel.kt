@file:Suppress("UNUSED_PARAMETER")

package com.glass.mouher.ui.profile

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.glass.domain.entities.UserProfileData
import com.glass.domain.usecases.cart.ICartUseCase
import com.glass.domain.usecases.user.IUserUseCase
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
    var addressStreet: String? = null

    @Bindable
    var addressNumber: String? = null

    @Bindable
    var addressState: String? = null

    @Bindable
    var addressMunicipality: String? = null

    @Bindable
    var addressCP: String? = null

    @Bindable
    var addressCountry: String? = null

    @Bindable
    var addressVisible: Boolean = true


    /**
     * Functions for buttons Edit or Delete on each card.
     */
    @Bindable
    var openProfileScreen: Fragment? = null

    @Bindable
    var onDiscard: String? = null


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
            userEmail = Correo
            userPhone = "Teléfono: $TelMovil"
            userBirthDate = "Fecha nacimiento: $FechaNac"

            notifyPropertyChanged(BR.userEmail)
            notifyPropertyChanged(BR.userPhone)
            notifyPropertyChanged(BR.userBirthDate)

            response.DomicilioEnvio?.get(0)?.let{ data->
                addressStreet = "Calle: ${data.Calle}"
                addressNumber = "N° ${data.NumExt}"
                addressMunicipality = data.Municipio
                addressCP = data.CP

                notifyPropertyChanged(BR.addressStreet)
                notifyPropertyChanged(BR.addressNumber)
                notifyPropertyChanged(BR.addressMunicipality)
                notifyPropertyChanged(BR.addressCP)

                if(addressStreet.isNullOrBlank()){
                    addressVisible = false
                    notifyPropertyChanged(BR.addressVisible)
                }

                getCountries(data.Pais, data.Estado)
            }
        }
    }


    private fun getCountries(countryId: Int?, stateId: Int?){
        addDisposable(userUseCase.getCountriesAsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list->
                    addressCountry = "País: ${ list.find { it.IdPais == countryId }?.Nombre }"
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
                    addressState = list.find { it.IdEstado == stateId }?.Nombre
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
        cartUseCase.deleteAllProductsOnCart()
    }


    private fun onError(t: Throwable?){
        progressVisible = false
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