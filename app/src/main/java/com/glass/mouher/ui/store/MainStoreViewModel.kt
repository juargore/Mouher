package com.glass.mouher.ui.store

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.usecases.cart.CartUseCase
import com.glass.domain.usecases.cart.ICartUseCase
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainStoreViewModel(
    private val context: Context,
    private val cartUseCase: ICartUseCase
): BaseViewModel() {

    @Bindable
    var openCart: Unit? = null

    @Bindable
    var totalProducts = "0"
        set(value){
            field = value
            notifyPropertyChanged(BR.totalProducts)
        }

    fun onCartClick(@Suppress("UNUSED_PARAMETER") view: View){
        Log.e("--", "click")
        notifyPropertyChanged(BR.openCart)
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        addDisposable(
            cartUseCase.getTotalProductsOnDb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    totalProducts = it.size.toString()
                }
        )
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}