@file:Suppress("UNUSED_PARAMETER")

package com.ocean.mouher.ui.history

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.ocean.domain.entities.HistoryUI
import com.ocean.domain.usecases.product.IProductUseCase
import com.ocean.mouher.App.Companion.context
import com.ocean.mouher.shared.General.getUserId
import com.ocean.mouher.ui.base.BaseViewModel
import com.ocean.mouher.ui.common.propertyChangedCallback
import com.ocean.mouher.utils.Validations.getMonthName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class HistoryViewModel(
    val productUseCase: IProductUseCase
): BaseViewModel() {

    var urlToDetails: String? = ""
    var hasErrors = true

    @Bindable
    var onDetailsClicked: Unit? = null

    @Bindable
    var openWebViewFragment: Unit? = null

    @Bindable
    var error: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.error)
        }

    @Bindable
    var showDatePickerPopUp: Unit? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.showDatePickerPopUp)
        }

    @Bindable
    var monthSelectedStr = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.monthSelectedStr)
        }

    @Bindable
    var historyItems: List<AHistoryListViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.historyItems)
        }

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }

    val itemPropertyChangedCallback =
        propertyChangedCallback { sender: Observable?, propertyId: Int ->
            if(sender is HistoryItemViewModel){
                when(propertyId){
                    BR.onDetailsClicked -> {
                        urlToDetails = sender.urlForDetails
                        notifyPropertyChanged(BR.openWebViewFragment)
                    }
                }
            }
        }

    init {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        getHistoryItemsList(month, year)
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    private fun getHistoryItemsList(month: Int, year: Int) {
        progressVisible = true
        addDisposable(productUseCase.getHistoryByUser(userId = getUserId(), startDate = "$year-$month-01")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onHistoryResponse, this::onError))
    }

    private fun onHistoryResponse(list: List<HistoryUI>) {
        val viewModels = mutableListOf<AHistoryListViewModel>()
        list.forEach {
            context?.let { c ->
                val viewModel = HistoryItemViewModel(context = c, history = it)
                viewModels.add(viewModel)
            }
        }
        historyItems = viewModels
        progressVisible = false
    }

    fun onSelectMonthButtonClicked(v: View?) {
        notifyPropertyChanged(BR.showDatePickerPopUp)
    }

    fun onSelectedMonthFromPopupClicked(month: Int, year: Int) {
        monthSelectedStr = "${getMonthName(context, month.toString())} $year"
        getHistoryItemsList(month, year)
    }

    fun onBackClicked(v: View) {
        notifyPropertyChanged(BR.backClicked)
    }

    private fun onError(t: Throwable?) {
        progressVisible = false
        hasErrors = true
        error = t?.message
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}