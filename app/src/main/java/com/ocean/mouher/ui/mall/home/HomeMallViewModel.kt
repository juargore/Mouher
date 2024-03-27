package com.ocean.mouher.ui.mall.home

import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.ocean.domain.entities.*
import com.ocean.domain.usecases.mall.IMallUseCase
import com.ocean.mouher.BR
import com.ocean.mouher.ui.base.BaseViewModel
import com.ocean.mouher.ui.common.completeUrlForImage
import com.ocean.mouher.ui.mall.MainActivityMall
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeMallViewModel(
    private val mallUseCase: IMallUseCase
): BaseViewModel() {

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }

    @Bindable
    var error: String? = null

    @Bindable
    var topBannerList: List<TopBannerUI> = listOf()

    @Bindable
    var urlImageTopLeft: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.urlImageTopLeft)
        }

    @Bindable
    var urlImageTopRight: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.urlImageTopRight)
        }

    @Bindable
    var sponsorStoresList: List<SponsorUI> = listOf()

    @Bindable
    var titleLobby: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleLobby)
        }

    @Bindable
    var descriptionLobby: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.descriptionLobby)
        }

    @Bindable
    var lobbyList: List<ItemLobby> = listOf()

    @Bindable
    var zonesList: List<ZoneUI> = listOf()


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        progressVisible = true

        addDisposable(mallUseCase.triggerToGetAllMallData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap mallUseCase.getTopBannerList()
            }

            .flatMap {
                onResponseTopBannerList(it)
                return@flatMap mallUseCase.getTwoTopImages()
            }

            .flatMap {
                onResponseTwoTopImages(it)
                return@flatMap mallUseCase.getLobbyData()
            }

            .flatMap {
                onResponseLobbyData(it)
                return@flatMap mallUseCase.getLogoImage()
            }

            .flatMap {
                onResponseLogoImage(it)
                return@flatMap mallUseCase.getSponsorsByMallId("")
            }

            .flatMap {
                onResponseSponsors(it)
                return@flatMap mallUseCase.getZonesByMall()
            }

            .subscribe(this::onResponseZones, this::onError)
        )
    }

    private fun onResponseLogoImage(logo: String) {
        MainActivityMall.setLogoOnToolbar(logo)
    }

    private fun onResponseTopBannerList(bannerList: List<TopBannerUI>) {
        topBannerList = bannerList
        notifyPropertyChanged(BR.topBannerList)
    }

    private fun onResponseTwoTopImages(twoImages: TopTwoImagesUI) {
        urlImageTopLeft = completeUrlForImage(twoImages.urlImageTopLeft)
        urlImageTopRight = completeUrlForImage(twoImages.urlImageTopRight)
    }

    private fun onResponseSponsors(sponsorsList: List<SponsorUI>) {
        sponsorStoresList = sponsorsList
        notifyPropertyChanged(BR.sponsorStoresList)

        progressVisible = false
    }

    private fun onResponseLobbyData(lobbyFullData: LobbyFullData) {
        titleLobby = lobbyFullData.title
        descriptionLobby = lobbyFullData.description

        lobbyFullData.listItemsLobby?.let{ list->
            lobbyList = list
            notifyPropertyChanged(BR.lobbyList)
        }
    }

    private fun onResponseZones(zoneList: List<ZoneUI>) {
        zonesList = zoneList
        notifyPropertyChanged(BR.zonesList)
    }

    private fun onError(t: Throwable?) {
        progressVisible = false

        t?.let {
            error = if (it.message != null) {
                if (it.message!!.contains("malformed JSON")) {
                    "En este momento no está disponible el servicio.\nPor favor inténtelo más tarde."
                } else {
                    it.localizedMessage
                }
            } else {
                it.localizedMessage
            }
            notifyPropertyChanged(BR.error)
        }
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}
