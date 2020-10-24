package com.glass.mouher.ui.mall.home

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.*
import com.glass.domain.usecases.mall.IMallUseCase
import com.glass.mouher.BR
import com.glass.mouher.BuildConfig
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.completeUrlForImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeMallViewModel(
    private val context: Context,
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
    var sponsorStoresList = mutableListOf<Item>()

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
    var zonesList = mutableListOf<Item>()


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

        progressVisible = false
    }

    private fun onResponseTwoTopImages(twoImages: TopTwoImagesUI){
        urlImageTopLeft = completeUrlForImage(twoImages.urlImageTopLeft)
        urlImageTopRight = completeUrlForImage(twoImages.urlImageTopRight)
    }

    private fun onResponseTopBannerList(bannerList: List<TopBannerUI>){

        bannerList.forEach {
            it.imageUrl = completeUrlForImage(it.imageUrl)
        }

        topBannerList = bannerList
        notifyPropertyChanged(BR.topBannerList)

        sponsorStoresList.clear()
        sponsorStoresList.add(Item(imageUrl = "https://i.pinimg.com/originals/75/b7/59/75b759a40bb58ac5afbdaea57455831d.jpg"))
        sponsorStoresList.add(Item(imageUrl = "https://bcassetcdn.com/public/blog/wp-content/uploads/2019/07/18094726/artisan-oz.jpg"))
        sponsorStoresList.add(Item(imageUrl = "https://i.etsystatic.com/10773810/r/il/5bda90/1718025006/il_570xN.1718025006_3wes.jpg"))
        sponsorStoresList.add(Item(imageUrl = "https://mir-s3-cdn-cf.behance.net/project_modules/1400/6cbf3568556191.5b611f672d5e3.jpg"))
        notifyPropertyChanged(BR.sponsorStoresList)

        zonesList.clear()
        zonesList.add(Item(name = "Zona 1", imageUrl = "https://image.freepik.com/free-photo/shopping-concept-close-up-portrait-young-beautiful-attractive-redhair-girl-smiling-looking-camera-with-shopping-bag-blue-pastel-background-copy-space_1258-856.jpg", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona 2", imageUrl = "https://image.shutterstock.com/image-photo/portrait-young-happy-smiling-woman-260nw-392415220.jpg", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona 3", imageUrl = "https://img.freepik.com/free-photo/portrait-pretty-woman-dress-holding-mobile-phone_171337-6983.jpg?size=626&ext=jpg&ga=GA1.2.186962824.1594857600", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona A", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona B", description = "Descripción zona"))
        notifyPropertyChanged(BR.zonesList)
    }

    private fun onError(t: Throwable?){
        progressVisible = false

        t?.let {
            error = it.localizedMessage
            notifyPropertyChanged(BR.error)
            Log.e("Error", it.localizedMessage)
        }
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}