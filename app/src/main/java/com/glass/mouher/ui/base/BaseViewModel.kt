package com.glass.mouher.ui.base

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import androidx.databinding.library.baseAdapters.BR
import com.glass.mouher.ui.common.AlertDialogViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel(), Observable {

    /**
     * If Non-null, an alert dialog should be displayed to the user.
     */
    @Bindable
    var errorPopupViewModel : AlertDialogViewModel? = null

    /**
     * Helper to display an alert dialog notifying an error to the user.
     * @param message the string id of the message
     * @param title the string id of the title of the popup.
     */
    protected fun displayErrorPopup(title: String, message: String) {
        errorPopupViewModel = AlertDialogViewModel(true, title, message)
        propertyChangeRegistry.notifyChange(this, BR.errorPopupViewModel)
    }

    /**
     * @property propertyChangeRegistry helper to make some properties of this object observable.
     */
    protected val propertyChangeRegistry = PropertyChangeRegistry()

    /**
     * Remove a callback, to not be notified for bindable anymore.
     */
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callback?.let {
            propertyChangeRegistry.remove(it)
        }
    }

    /**
     * Add a callback to be notified of changes on the bindable fields.
     */
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callback?.let {
            propertyChangeRegistry.add(it)
        }
    }

    /**
     * Notify a bindable property change.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        propertyChangeRegistry.notifyCallbacks(this, fieldId, null)
    }

    /**
     * Bag of the disposables, to be disposed onPause.
     */
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Add a disposable to the bag of disposable, dispoed onPause.
     */
    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }

    /**
     * @property compositeStartStopDisposable disposables that will be dismissed onStop.
     */
    private val compositeStartStopDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Add a disposable which will be dismissed onStop.
     */
    protected fun addStartStopDisposable(disposable: Disposable) {
        compositeStartStopDisposable.add(disposable)
    }

    abstract fun onResume(callback: Observable.OnPropertyChangedCallback?)
    abstract fun onPause(callback: Observable.OnPropertyChangedCallback?)

    /**
     * Dismiss "startStop" disposables.
     */
    open fun onStop() {
        compositeStartStopDisposable.clear()
    }

}