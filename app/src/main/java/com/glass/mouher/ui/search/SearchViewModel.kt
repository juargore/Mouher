@file:Suppress("UNUSED_PARAMETER")
package com.glass.mouher.ui.search

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.ProductSearchUI
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(
    private val productUseCase: IProductUseCase
): BaseViewModel() {

    @Bindable
    var textToSearch = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.textToSearch)
        }

    @Bindable
    var results: List<ProductSearchUI> = listOf()

    @Bindable
    var showEmptyMsg = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showEmptyMsg)
        }

    @Bindable
    var searching = ""
        set(value) {
            field = "Búsqueda de: $value"
            notifyPropertyChanged(BR.searching)
        }

    @Bindable
    var found = ""
        set(value) {
            field = "${results.size} producto(s) encontrado(s)"
            notifyPropertyChanged(BR.found)
        }

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    fun searchOnServer() {
        progressVisible = true
        addDisposable(productUseCase.getSearchResults(textToSearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onSearchResults, this::onError))
    }

    private fun onSearchResults(list: List<ProductSearchUI>) {
        searching = textToSearch
        results = list
        found = ""
        notifyPropertyChanged(BR.results)

        showEmptyMsg = list.isEmpty()
        progressVisible = false
    }

    private fun onError(t: Throwable?) {
        progressVisible = false
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}