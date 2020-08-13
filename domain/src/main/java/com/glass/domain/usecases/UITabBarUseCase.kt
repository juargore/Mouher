package com.glass.domain.usecases

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class UITabBarUseCase(): IUITabBarUseCase {

    private val currentTab: BehaviorSubject<Pair<IUITabBarUseCase.Tabs, Boolean>>
            = BehaviorSubject.createDefault(Pair(IUITabBarUseCase.Tabs.HOME, false))

    override fun setCurrentTab(tab: IUITabBarUseCase.Tabs) {
        currentTab.onNext(Pair(tab, true))
    }

    override fun getCurrentTab(): IUITabBarUseCase.Tabs  =
        currentTab.value?.first ?: IUITabBarUseCase.Tabs.HOME

    override fun getTabObservable(): Observable<IUITabBarUseCase.Tabs> =
        currentTab.map { it.first }.distinctUntilChanged()
}