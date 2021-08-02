package com.glass.mouher.ui.store.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.*
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.domain.usecases.store.IStoreUseCase
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.utils.isEmailValid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeStoreViewModel(
    private val storeUseCase: IStoreUseCase,
    private val productUseCase: IProductUseCase
): BaseViewModel(), ClickHandler<AStoreCategoryViewModel> {

    var storeId = 1 // 1 is default
    var categoryId = 1 // 1 is default
    var categoryName = ""

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    @Bindable
    var onClick: Unit? = null

    @Bindable
    var items: List<AStoreCategoryViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    @Bindable
    var bannerList: List<TopBannerUI> = listOf()

    @Bindable
    var itemsNewProducts: List<ProductUI> = listOf()

    @Bindable
    var sponsorStoresList: List<SponsorUI> = listOf()

    @Bindable
    var urlVideo = ""

    @Bindable
    var urlImageVideo = ""


    @Bindable
    var userNameToSubscribe: String? = null


    @Bindable
    var emailToSubscribe: String? = null

    @Bindable
    var error = ResponseUI()
        set(value) {
            field = value
            notifyPropertyChanged(BR.error)
        }

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }

    fun initialize(c: Context, id: String){
        context = c
        storeId = id.toInt()
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        progressVisible = true

        addDisposable(storeUseCase.triggerToGetStoreData(storeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap storeUseCase.getTopBannerList()
            }

            .flatMap {
                onTopBannerListResponse(it)
                return@flatMap storeUseCase.getImageVideo()
            }

            .flatMap {
                onUrlImageVideoResponse(it)
                return@flatMap storeUseCase.getUrlVideo()
            }

            .flatMap {
                onUrlVideoResponse(it)
                return@flatMap storeUseCase.getNewArrivalsForStore()
            }

            .flatMap {
                onNewArrivalsResponse(it)
                return@flatMap storeUseCase.getCategoriesByStore()
            }

            .flatMap {
                onCategoriesResponse(it)
                return@flatMap storeUseCase.getSponsorsByStore()
            }

            .subscribe(this::onResponseSponsors, this::onError)
        )
    }

    private fun onCategoriesResponse(list: List<CategoryUI>){
        val viewModels = mutableListOf<AStoreCategoryViewModel>()

        list.forEach {
            val viewModel = StoreCategoryItemViewModel(context = context, category = it)
            viewModels.add(viewModel)
        }

        items = viewModels
    }

    private fun onNewArrivalsResponse(list: List<ProductUI>){
        itemsNewProducts = list
        notifyPropertyChanged(BR.itemsNewProducts)
    }

    private fun onResponseSponsors(sponsorsList: List<SponsorUI>){
        sponsorStoresList = sponsorsList
        notifyPropertyChanged(com.glass.mouher.BR.sponsorStoresList)

        progressVisible = false
    }

    private fun onTopBannerListResponse(list: List<TopBannerUI>){
        bannerList = list
        notifyPropertyChanged(BR.bannerList)
    }

    private fun onUrlVideoResponse(url: String){
        urlVideo = url
        notifyPropertyChanged(BR.urlVideo)
    }

    private fun onUrlImageVideoResponse(url: String){
        urlImageVideo = url
        notifyPropertyChanged(BR.urlImageVideo)
    }

    private fun onError(t: Throwable?){
        Log.e("--", t?.localizedMessage.toString())
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

    fun onSubscribeButtonClicked(v: View?){
        if(userNameToSubscribe.isNullOrBlank() || emailToSubscribe.isNullOrBlank()){
            error = ResponseUI(hasErrors = false, message = "Llene ambos campos para la suscripción")
            return
        }

        emailToSubscribe?.let{
            if(it.isEmailValid()){
                progressVisible = true
                addDisposable(storeUseCase.subscribeUserToNewsletter(
                    userName = userNameToSubscribe?.trim() ?: "",
                    email = emailToSubscribe?.trim() ?: "",
                    storeId = storeId
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ responseUI->
                        progressVisible = false
                        error = ResponseUI(
                            hasErrors = responseUI.hasErrors,
                            message = responseUI.message
                        )
                    }, this::onError))
            }else{
                error = ResponseUI(hasErrors = false, message = "Ingrese un email válido")
                return
            }
        }
    }

    override fun onClick(viewModel: AStoreCategoryViewModel) {
        if(viewModel is StoreCategoryItemViewModel){
            categoryId = viewModel.id ?: 1
            categoryName = viewModel.name ?: ""
            notifyPropertyChanged(BR.onClick)
        }
    }
}