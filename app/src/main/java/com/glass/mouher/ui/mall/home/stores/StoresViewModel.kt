@file:Suppress("UNUSED_PARAMETER")

package com.glass.mouher.ui.mall.home.stores

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.StoreInZoneUI
import com.glass.domain.usecases.mall.IMallUseCase
import com.glass.mouher.App.Companion.context
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StoresViewModel(
    private val mallUseCase: IMallUseCase
): BaseViewModel(), ClickHandler<AStoresViewModel> {

    @Bindable
    var onClose: Unit? = null

    @Bindable
    var error: String? = null

    @Bindable
    var openStoreWithId: Int? = null

    @Bindable
    var zoneName: String? = null

    @Bindable
    var zoneId: String = "0"

    @Bindable
    var items: List<AStoresViewModel> = listOf()
        set(value){
            field = value
            rvVisible = !value.isNullOrEmpty()
            emptyTextVisible = value.isNullOrEmpty()
        }

    @Bindable
    var rvVisible: Boolean? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.rvVisible)
        }

    @Bindable
    var emptyTextVisible: Boolean? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.emptyTextVisible)
        }

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }


    fun initialize(zoneName: String?, zoneId: String?){
        this.zoneName = zoneName
        this.zoneId = zoneId ?: "0"

        notifyPropertyChanged(BR.zoneName)
        notifyPropertyChanged(BR.zoneId)
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        progressVisible = true

        addDisposable(mallUseCase.getStoresByZone(zoneId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponseStoresByZone, this::onError))
    }

    private fun onResponseStoresByZone(storesList: List<StoreInZoneUI>){
        val viewModels = mutableListOf<AStoresViewModel>()

        context?.let{ c->
            storesList.forEach {
                val viewModel = StoreItemViewModel(context = c, store = it)
                viewModels.add(viewModel)
            }
        }

        items = viewModels
        notifyPropertyChanged(BR.items)

        progressVisible = false
    }

    private fun onError(t: Throwable){
        progressVisible = false
        error = t.localizedMessage
        notifyPropertyChanged(BR.error)
    }

    fun onBackClicked(v: View?){
        notifyPropertyChanged(BR.onClose)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }

    override fun onClick(viewModel: AStoresViewModel) {
        if(viewModel is StoreItemViewModel){
            openStoreWithId = viewModel.id
            notifyPropertyChanged(BR.openStoreWithId)
        }
    }
}