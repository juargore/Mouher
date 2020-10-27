package com.glass.mouher.ui.history

import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.propertyChangedCallback

class HistoryViewModel(
    private val context: Context
): BaseViewModel() {

    @Bindable
    var deleteItem: Unit? = null

    @Bindable
    var historyItems: List<AHistoryListViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.historyItems)
        }

    /**
    * @property itemPropertyChangedCallback listener to item actions
    */
    val itemPropertyChangedCallback =
        propertyChangedCallback { sender: Observable?, propertyId: Int ->
            if(sender is HistoryItemViewModel){
                when(propertyId){
                    BR.deleteClicked -> notifyPropertyChanged(BR.deleteItem)
                }
            }
        }


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
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