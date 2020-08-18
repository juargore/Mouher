package com.glass.mouher.ui.mall

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.usecases.IUITabBarUseCase
import com.glass.domain.usecases.IUITabBarUseCase.Tabs
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(
    private val tabBarUseCase: IUITabBarUseCase,
    private val context: Context
): BaseViewModel() {

    /**
     * @property currentTab The currently selected section (tab)
     */
    @Bindable
    var currentTab: Tabs = Tabs.HOME
        private set

    @Bindable
    var cartScreen: Unit? = null

    @Bindable
    var backPressed: Unit? = null

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        addDisposable(tabBarUseCase.getTabObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateSelectedTab){
                Log.e("--", it.localizedMessage)
            })
    }

    private fun updateSelectedTab(tab: Tabs) {
        currentTab = tab
        notifyPropertyChanged(BR.currentTab)
    }

    fun onBackPressed(@Suppress("UNUSED_PARAMETER") v: View) {
        notifyPropertyChanged(BR.backPressed)
    }

    fun onCartPressed(@Suppress("UNUSED_PARAMETER") v: View) {
        notifyPropertyChanged(BR.cartScreen)
    }

    fun onTabSelected(tab: Tabs) {
        tabBarUseCase.setCurrentTab(tab)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}