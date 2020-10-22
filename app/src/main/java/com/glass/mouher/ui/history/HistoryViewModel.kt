package com.glass.mouher.ui.history

import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.domain.usecases.history.IHistoryUseCase
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.propertyChangedCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HistoryViewModel(
    private val context: Context,
    private val orderUseCase: IHistoryUseCase
): BaseViewModel() {

    @Bindable
    var deleteItem: Unit? = null

    @Bindable
    var historyItems: List<AHistoryListViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.historyItems)
        }

    val itemPropertyChangeCallback =
        propertyChangedCallback { sender: Observable?, propertyId: Int ->
            if(sender is HistoryItemViewModel){
                when(propertyId){
                    BR.deleteClicked -> sender.deleteClicked?.let{
                        notifyPropertyChanged(BR.deleteItem)
                    }
                }
            }
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        addDisposable(orderUseCase.getHistoryByUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponse, this::onError))
    }

    private fun onResponse(list: List<Item>){
        val viewModels = mutableListOf<AHistoryListViewModel>()

        list.forEach {
            val viewModel = HistoryItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        historyItems = viewModels
    }

    private fun onError(t: Throwable?){
        Log.e("Error on History", "${t?.localizedMessage}")
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}