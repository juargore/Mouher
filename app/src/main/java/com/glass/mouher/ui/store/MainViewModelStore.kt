package com.glass.mouher.ui.store

import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.usecases.IUITabBarUseCase
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModelStore(
    private val tabBarUseCase: IUITabBarUseCase,
    private val context: Context
): BaseViewModel() {

    /**
     * @property currentTab The currently selected section (tab)
     */
    @Bindable
    var currentTab: IUITabBarUseCase.Tabs = IUITabBarUseCase.Tabs.HOME_STORE
        private set


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        addDisposable(tabBarUseCase.getTabObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateSelectedTab){
                Log.e("--", it.localizedMessage)
            })
    }

    private fun updateSelectedTab(tab: IUITabBarUseCase.Tabs) {
        currentTab = tab
        notifyPropertyChanged(BR.currentTab)
    }

    fun onBackPressed() {
        //loaderUseCase.hideAll()
    }

    fun onTabSelected(tab: IUITabBarUseCase.Tabs) {
        tabBarUseCase.setCurrentTab(tab)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}