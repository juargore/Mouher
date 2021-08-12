@file:Suppress("UNUSED_PARAMETER")
package com.glass.mouher.ui.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.CategoryUI
import com.glass.domain.entities.ContactUI
import com.glass.domain.entities.SocialMediaUI
import com.glass.domain.entities.ZoneUI
import com.glass.domain.usecases.mall.IMallUseCase
import com.glass.domain.usecases.store.IStoreUseCase
import com.glass.mouher.R
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MenuViewModel(
    private val mallUseCase: IMallUseCase,
    private val storeUseCase: IStoreUseCase
): BaseViewModel(), ClickHandler<AMenuViewModel> {

    var storeIdSelectedOnMenu: Int = 0

    private var storeNameSelectedOnMenu: String = ""

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    private var source: String? = null

    @Bindable
    var screen: MENU? = null

    @Bindable
    var stringZoneOrCategory = "Zonas Comerciales"

    @Bindable
    var openZoneSelected: ZoneUI? = null

    @Bindable
    var stringMouherOrStore = "Mouher Market"

    @Bindable
    var openProfileScreen: Unit? = null

    @Bindable
    var openHistoryScreen: Unit? = null

    @Bindable
    var address = ""

    @Bindable
    var phone = ""

    @Bindable
    var email = ""

    @Bindable
    var workHours = ""

    @Bindable
    var urlOportunities = ""

    @Bindable
    var urlPrivacyPolicy = ""

    @Bindable
    var urlTermsAndConditions = ""

    @Bindable
    var isUserLoggedIn = false
        set(value){
            field = value
            layoutUserLoggedInVisible = value
            layoutUserNotLoggedInVisible = !value
            profileSectionVisible = value
        }

    @Bindable
    var logoMouher = ""

    @Bindable
    var layoutUserLoggedInVisible: Boolean? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.layoutUserLoggedInVisible)
        }

    @Bindable
    var layoutUserNotLoggedInVisible: Boolean? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.layoutUserNotLoggedInVisible)
        }

    @Bindable
    val userPhoto = R.drawable.face

    @Bindable
    var profileSectionVisible: Boolean? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.profileSectionVisible)
        }

    @Bindable
    var socialMediaVisible: Boolean? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.socialMediaVisible)
        }

    @Bindable
    var zonesSectionVisible: Boolean = true
        set(value){
            field = value
            rotateAnimationForZonesSection = if(value) 90 else -90
        }

    @Bindable
    var rotateAnimationForZonesSection: Int? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.rotateAnimationForZonesSection)
        }

    @Bindable
    var mouherSectionVisible: Boolean = true
        set(value){
            field = value
            rotateAnimationForMouherSection = if(value) 90 else -90
        }

    @Bindable
    var rotateAnimationForMouherSection: Int? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.rotateAnimationForMouherSection)
        }

    @Bindable
    var searchProductVisibility: Boolean = false
        set(value){
            field = value
            notifyPropertyChanged(BR.searchProductVisibility)
        }

    @Bindable
    var itemsSocial: List<SocialMediaUI> = listOf()

    @Bindable
    var items: List<AMenuViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    @Bindable
    var categories: List<CategoryUI> = listOf()

    @Bindable
    var versionStr = ""
        set(value){
            field = value
            notifyPropertyChanged(BR.versionStr)
        }


    fun initialize(c: Context, s: String){
        context = c
        source = s

        //TODO
        isUserLoggedIn = false
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
        getCurrentVersionCode()

        if(!isUserLoggedIn){
            // If user hasn't signed in -> Load the Mall logo instead of his/her photo
            addDisposable(mallUseCase.triggerToGetAllMallData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap {
                        return@flatMap mallUseCase.getLogoImage()
                    }.subscribe(this::onResponseLogo, this::onError)
            )
        }else{
            // User already signed in -> Just load the zones
            onResponseLogo(null)
        }

        addDisposable(mallUseCase.getSocialMedia(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onResponseMenuSocialMediaItems, this::onError))

        /*
        addDisposable(mallUseCase.getContactInformation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onContactResponse, this::onError))*/
    }

    private fun onContactResponse(contact: ContactUI){
        address = contact.address ?: ""
        phone = contact.phone ?: ""
        email = contact.email ?: ""
        workHours = contact.workHours ?: ""
        urlOportunities = contact.urlOportunities ?: ""
        urlPrivacyPolicy = contact.urlPrivacyPolicy ?: ""
        urlTermsAndConditions = contact.urlTermsAndConditions ?: ""

        notifyPropertyChanged(BR.address)
        notifyPropertyChanged(BR.phone)
        notifyPropertyChanged(BR.email)
        notifyPropertyChanged(BR.workHours)
        notifyPropertyChanged(BR.urlOportunities)
        notifyPropertyChanged(BR.urlPrivacyPolicy)
        notifyPropertyChanged(BR.urlTermsAndConditions)
    }

    private fun getCurrentVersionCode(){
        context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_ACTIVITIES).apply {
                versionStr = "v${versionName}"
        }
    }

    private fun onResponseLogo(urlLogo: String?){
        urlLogo?.let{
            logoMouher = it
            notifyPropertyChanged(BR.logoMouher)
        }

        if(source == "MALL"){
            stringZoneOrCategory = "Zonas Comerciales"
            stringMouherOrStore = "Mouher Market"
            socialMediaVisible = true

            addDisposable(mallUseCase.getZonesForMenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onResponseMenuItems, this::onError))
        }else{
            stringZoneOrCategory = "Categorías de Productos"
            stringMouherOrStore = "Sobre la tienda"
            socialMediaVisible = false

            // Wait a little while data is downloaded from StoreViewModel
            Handler().postDelayed({
                addDisposable(storeUseCase.getCategoriesByStore()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onStoreCategoriesResponse, this::onError))
            }, 1000)
        }

        notifyPropertyChanged(BR.stringZoneOrCategory)
        notifyPropertyChanged(BR.stringMouherOrStore)
    }

    private fun onStoreCategoriesResponse(list: List<CategoryUI>){
        categories = list
        notifyPropertyChanged(BR.categories)

        addDisposable(storeUseCase.getStoreSimpleInformation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                storeIdSelectedOnMenu = it.substringBefore("-").toInt()
                storeNameSelectedOnMenu = it.substringAfter("-")
            }, this::onError))
    }

    private fun onResponseMenuItems(zonesList: List<ZoneUI>){
        notifyPropertyChanged(BR.isUserLoggedIn)

        val viewModels = mutableListOf<AMenuViewModel>()

        zonesList.forEach {
            val viewModel = MenuItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        items = viewModels
    }

    private fun onResponseMenuSocialMediaItems(socialList: List<SocialMediaUI>){
        itemsSocial = socialList
        notifyPropertyChanged(BR.itemsSocial)
    }

    fun onSignInClick(view: View){
        screen = MENU.LOGIN
        notifyPropertyChanged(BR.screen)
    }

    fun onProfileClick(view: View){
        screen = MENU.PROFILE
        notifyPropertyChanged(BR.screen)
    }

    fun onHistoryClick(view: View){
        screen = MENU.HISTORY
        notifyPropertyChanged(BR.screen)
    }

    fun onAboutClick(view: View){
        screen = MENU.ABOUT
        notifyPropertyChanged(BR.screen)
    }

    fun onContactUsClick(view: View){
        screen = MENU.CONTACT
        notifyPropertyChanged(BR.screen)
    }

    fun onExtraServicesClick(view: View){
        screen = MENU.EXTRA_SERVICES
        notifyPropertyChanged(BR.screen)
    }

    fun onMoreInfoClick(view: View){
        screen = MENU.MORE_INFO
        notifyPropertyChanged(BR.screen)
    }

    private fun onError(t: Throwable?){
        Log.e("ERROR", t?.localizedMessage.toString())
    }

    fun onShowZonesSectionClick(view: View){
        zonesSectionVisible = !zonesSectionVisible
        notifyPropertyChanged(BR.zonesSectionVisible)
    }

    fun onShowMouherSectionClick(view: View){
        mouherSectionVisible = !mouherSectionVisible
        notifyPropertyChanged(BR.mouherSectionVisible)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

    override fun onClick(viewModel: AMenuViewModel) {
        if(viewModel is MenuItemViewModel){
            openZoneSelected = viewModel.zoneUI
            notifyPropertyChanged(BR.openZoneSelected)
        }
    }
}

    enum class MENU{
        LOGIN,
        PROFILE,
        HISTORY,
        ABOUT,
        CONTACT,
        EXTRA_SERVICES,
        MORE_INFO
    }