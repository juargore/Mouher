package com.glass.mouher.ui.menu

import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.SocialMediaUI
import com.glass.domain.entities.ZoneUI
import com.glass.domain.usecases.mall.IMallUseCase
import com.glass.mouher.R
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.ui.common.completeUrlForImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MenuViewModel(
    private val context: Context,
    private val mallUseCase: IMallUseCase
): BaseViewModel(), ClickHandler<AMenuViewModel> {

    private var source: String? = null

    @Bindable
    var screen: MENU? = null

    @Bindable
    var openZoneSelected: ZoneUI? = null

    @Bindable
    var openProfileScreen: Unit? = null

    @Bindable
    var openHistoryScreen: Unit? = null


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
    var zonesSectionVisible: Boolean = false
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
    var mouherSectionVisible: Boolean = false
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
    var versionStr = ""
        set(value){
            field = value
            notifyPropertyChanged(BR.versionStr)
        }


    fun initialize(source: String){
        this.source = source
        searchProductVisibility = source != "MALL"
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
        getCurrentVersionCode()

        isUserLoggedIn = true

        if(!isUserLoggedIn){
            addDisposable(mallUseCase.getTopBannerList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap {
                        mallUseCase.getLogoImage()
                    }
                    .subscribe(this::onResponseLogo, this::onError)
            )
        }

        addDisposable(mallUseCase.getSocialMedia()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onResponseMenuSocialMediaItems, this::onError)
        )

        addDisposable(mallUseCase.getZonesByMall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponseMenuItems, this::onError)
        )
    }

    private fun getCurrentVersionCode(){
        context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_ACTIVITIES).apply {
                versionStr = "v${versionName}"
        }
    }

    private fun onResponseLogo(urlLogo: String){
        logoMouher = completeUrlForImage(urlLogo)
        notifyPropertyChanged(BR.logoMouher)
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
        socialList.forEach {
            it.urlImage = completeUrlForImage(it.urlImage)
        }

        itemsSocial = socialList
        notifyPropertyChanged(BR.itemsSocial)
    }

    fun onSignInClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.LOGIN
        notifyPropertyChanged(BR.screen)
    }

    fun onProfileClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.PROFILE
        notifyPropertyChanged(BR.screen)
    }

    fun onHistoryClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.HISTORY
        notifyPropertyChanged(BR.screen)
    }

    fun onAboutClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.ABOUT
        notifyPropertyChanged(BR.screen)
    }

    fun onContactUsClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.CONTACT
        notifyPropertyChanged(BR.screen)
    }

    fun onExtraServicesClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.EXTRA_SERVICES
        notifyPropertyChanged(BR.screen)
    }

    fun onMoreInfoClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.MORE_INFO
        notifyPropertyChanged(BR.screen)
    }

    private fun onError(t: Throwable?){
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        Log.e("ERROR", t?.localizedMessage)
    }

    fun onShowZonesSectionClick(@Suppress("UNUSED_PARAMETER") view: View){
        zonesSectionVisible = !zonesSectionVisible
        notifyPropertyChanged(BR.zonesSectionVisible)
    }

    fun onShowMouherSectionClick(@Suppress("UNUSED_PARAMETER") view: View){
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