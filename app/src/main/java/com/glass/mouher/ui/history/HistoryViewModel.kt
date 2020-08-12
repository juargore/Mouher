package com.glass.mouher.ui.history

import android.content.Context
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.domain.usecases.history.IHistoryUseCase
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HistoryViewModel(
    private val context: Context,
    private val orderUseCase: IHistoryUseCase
): BaseViewModel() {

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val disposable = orderUseCase.getHistoryByUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponse, this::onError)

        addDisposable(disposable)
    }

    private fun onResponse(list: List<Item>){

    }

    private fun onError(t: Throwable?){

    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}