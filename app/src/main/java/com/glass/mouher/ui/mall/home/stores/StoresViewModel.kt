package com.glass.mouher.ui.mall.home.stores

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.StoreInZoneUI
import com.glass.domain.usecases.mall.IMallUseCase
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.ui.common.completeUrlForImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StoresViewModel(
    private val context: Context,
    private val mallUseCase: IMallUseCase
): BaseViewModel(), ClickHandler<AStoresViewModel> {

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }

    @Bindable
    var onClose: Unit? = null

    @Bindable
    var error: String? = null

    @Bindable
    var openStoreWithId: String? = null

    @Bindable
    var zoneName: String? = null

    @Bindable
    var zoneId: String? = null

    @Bindable
    var items: List<AStoresViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }


    fun initialize(zoneName: String?, zoneId: String?){
        this.zoneName = zoneName
        this.zoneId = zoneId

        notifyPropertyChanged(BR.zoneName)
        notifyPropertyChanged(BR.zoneId)
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        progressVisible = true

        addDisposable(mallUseCase.getStoresByZone(zoneId ?: "0")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponseStoresByZone, this::onError)
        )
    }

    private fun onResponseStoresByZone(storesList: List<StoreInZoneUI>){
        val viewModels = mutableListOf<AStoresViewModel>()

        storesList.forEach {
            it.urlImage = completeUrlForImage(it.urlImage)

            val viewModel = StoreItemViewModel(context = context, store = it)
            viewModels.add(viewModel)
        }

        items = viewModels
        progressVisible = false
    }

    private fun onError(t: Throwable?){
        progressVisible = false

        t?.let {
            error = it.localizedMessage
            notifyPropertyChanged(BR.error)
        }
    }

    fun onBackClicked(@Suppress("UNUSED_PARAMETER") v: View){
        notifyPropertyChanged(BR.onClose)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

    override fun onClick(viewModel: AStoresViewModel) {
        if(viewModel is StoreItemViewModel){
            openStoreWithId = viewModel.id
            notifyPropertyChanged(BR.openStoreWithId)
        }
    }
}