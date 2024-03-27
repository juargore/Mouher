@file:Suppress("UNUSED_PARAMETER")

package com.ocean.mouher.ui.store

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.ocean.domain.usecases.cart.ICartUseCase
import com.ocean.domain.usecases.store.IStoreUseCase
import com.ocean.mouher.shared.General
import com.ocean.mouher.ui.base.BaseViewModel
import com.ocean.mouher.ui.common.completeUrlForImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainStoreViewModel(
    private val cartUseCase: ICartUseCase,
    private val storeUseCase: IStoreUseCase
): BaseViewModel() {

    private var storeId: Int = 0

    @Bindable
    var urlImageLogo = ""

    @Bindable
    var openCart: Unit? = null

    @Bindable
    var openSearch: Unit? = null

    @Bindable
    var totalProducts = "0"
        set(value){
            field = value
            notifyPropertyChanged(BR.totalProducts)
        }

    fun onCartClick(v: View?){
        notifyPropertyChanged(BR.openCart)
    }

    fun onSearchClick(v: View?){
        notifyPropertyChanged(BR.openSearch)
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        storeId = MainStoreActivity.storeId

        addDisposable(storeUseCase.triggerToGetStoreData(storeId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap storeUseCase.getStoreLogo()
            }
            .subscribe(this::onUrlImageLogoResponse, this::onError)
        )

        addDisposable(cartUseCase.getSizeProductsOnDb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { totalProducts = it.toString() })

        checkIfStoreIdIsDifferent()
    }

    private fun onUrlImageLogoResponse(url: String){
        urlImageLogo = completeUrlForImage(url)
        notifyPropertyChanged(BR.urlImageLogo)
    }

    fun clearProductsFromCart(){
        General.saveCartNotes("")
        cartUseCase.deleteAllProductsOnCart()
    }

    private fun checkIfStoreIdIsDifferent(){
        addDisposable(cartUseCase.getTotalProductsOnDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list->
                list.firstOrNull()?.let{
                    if(it.storeId != storeId){
                        clearProductsFromCart()
                    }
                }

            }, this::onError)
        )
    }

    @Suppress("EmptyMethod")
    private fun onError(t: Throwable?){

    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}