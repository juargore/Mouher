package com.glass.mouher.ui.history

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.domain.usecases.history.IHistoryUseCase
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HistoryViewModel(
    private val context: Context,
    private val orderUseCase: IHistoryUseCase
): BaseViewModel() {

    @Bindable
    var items: List<AHistoryListViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val disposable = orderUseCase.getHistoryByUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponse, this::onError)

        addDisposable(disposable)
    }

    private fun onResponse(list: List<Item>){
        val mList = list.toMutableList()
        mList.add(Item( name = "Carlos Esparza C.", description = "Av. Acueducto 1100 Col. Aviación"))
        mList.add(Item( name = "María Pérez Díaz", description = "Av. Acueducto 1100 Col. Aviación"))
        mList.add(Item( name = "Carlos Esparza C.", description = "Av. Acueducto 1100 Col. Aviación"))
        mList.add(Item( name = "Carlos Esparza C.", description = "Av. Acueducto 1100 Col. Aviación"))
        mList.add(Item( name = "María Pérez Díaz", description = "Av. Acueducto 1100 Col. Aviación"))
        mList.add(Item( name = "María Pérez Díaz", description = "Av. Acueducto 1100 Col. Aviación"))

        val viewModels = mutableListOf<AHistoryListViewModel>()

        mList.forEach {
            val viewModel = HistoryItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        items = viewModels
    }

    private fun onError(t: Throwable?){

    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}