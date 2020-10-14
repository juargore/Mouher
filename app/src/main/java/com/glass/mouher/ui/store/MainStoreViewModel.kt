package com.glass.mouher.ui.store

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.mouher.ui.base.BaseViewModel

class MainStoreViewModel(
    private val context: Context
): BaseViewModel() {

    @Bindable
    var openCart: Unit? = null

    @Bindable
    var totalProducts = "5"
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
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}