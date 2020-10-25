package com.glass.mouher.ui.mall.home

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.*
import com.glass.domain.usecases.mall.IMallUseCase
import com.glass.domain.usecases.store.IStoreUseCase
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.completeUrlForImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeMallViewModel(
    private val context: Context,
    private val mallUseCase: IMallUseCase,
    private val storeUseCase: IStoreUseCase
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
        set(value){
            field = value
            notifyPropertyChanged(BR.urlImageTopLeft)
        }

    @Bindable
    var urlImageTopRight: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.urlImageTopRight)
        }

    @Bindable
    var sponsorStoresList: List<SponsorStoreUI> = listOf()

    @Bindable
    var titleLobby: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.titleLobby)
        }

    @Bindable
    var descriptionLobby: String? = null
        set(value){
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

        addDisposable(mallUseCase.getTopBannerList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .flatMap {
                onResponseTopBannerList(it)
                return@flatMap mallUseCase.getTwoTopImages()
            }

            .flatMap {
                onResponseTwoTopImages(it)
                return@flatMap mallUseCase.getLobbyData()
            }

            .subscribe(this::onResponseLobbyData, this::onError)
        )

        addDisposable(storeUseCase.getSponsorStoresByMall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponseSponsorStores, this::onError)
        )

        addDisposable(mallUseCase.getZonesByMall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponseZones, this::onError)
        )
    }

    private fun onResponseTopBannerList(bannerList: List<TopBannerUI>){

        bannerList.forEach {
            it.imageUrl = completeUrlForImage(it.imageUrl)
        }

        topBannerList = bannerList
        notifyPropertyChanged(BR.topBannerList)
    }

    private fun onResponseTwoTopImages(twoImages: TopTwoImagesUI){
        urlImageTopLeft = completeUrlForImage(twoImages.urlImageTopLeft)
        urlImageTopRight = completeUrlForImage(twoImages.urlImageTopRight)
    }

    private fun onResponseSponsorStores(storesList: List<SponsorStoreUI>){
        storesList.forEach {
            it.urlImage = completeUrlForImage(it.urlImage)
        }

        sponsorStoresList = storesList
        notifyPropertyChanged(BR.sponsorStoresList)

        progressVisible = false
    }

    private fun onResponseLobbyData(lobbyData: LobbyData){
        titleLobby = lobbyData.title
        descriptionLobby = lobbyData.description

        lobbyData.listItemsLobby?.let{ list->
            list.forEach {
                it.urlImage = completeUrlForImage(it.urlImage)
            }

            lobbyList = list
            notifyPropertyChanged(BR.lobbyList)
        }
    }

    private fun onResponseZones(zonesList: List<ZoneUI>){
        zonesList.forEach {
            it.urlImage = completeUrlForImage(it.urlImage)
        }

        this.zonesList = zonesList
        notifyPropertyChanged(BR.zonesList)
    }

    private fun onError(t: Throwable?){
        progressVisible = false

        t?.let {
            error = it.localizedMessage
            notifyPropertyChanged(BR.error)
        }
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}