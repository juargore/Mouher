package com.glass.mouher.ui.store.home

import android.content.Context
import androidx.databinding.Observable
import com.glass.mouher.ui.base.BaseViewModel

class HomeStoreViewModel(
    private val context: Context
): BaseViewModel() {

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}