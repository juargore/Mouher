package com.glass.domain.usecases

import io.reactivex.Observable

/**
 * Prepare badge data to display on tabbar.
 * Track which tab is currently visible to the user.
 */
interface IUITabBarUseCase {
    /**
     * All the tabs of the online activity.
     */
    enum class Tabs {
        PROFILE,
        HISTORY,
        CATEGORIES,
        USER_LIST
    }

    /**
     * Inform the Usecase of the currently selected tab
     * @param tab the tab actualy visible to the user.
     * @param fromNotification true if this current tab update come from a notification reception.
     */
    fun setCurrentTab(tab: Tabs)

    /**
     * Get the currently selected tab.
     * @return the currently selected Tab.
     */
    fun getCurrentTab(): Tabs

    /**
     * Observe the currently selected tab.
     * @return the tab, as an observable.
     */
    fun getTabObservable(): Observable<Tabs>
}
