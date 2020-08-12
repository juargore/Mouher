package com.glass.mouher.ui.common

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.mouher.R

class AlertDialogViewModel(

    // default popup
    var title: String = "Default title",
    var message: String = "Default message",
    var hasMessage: Boolean = true,
    var icon: Int = R.drawable.common_google_signin_btn_icon_dark,
    var hasPositive: Boolean = true,
    var positiveLabel: Int = R.string.app_name,
    var hasNegative: Boolean = true,
    var negativeLabel: Int = R.string.app_name,
    var isError: Boolean = false,
    callback: Observable.OnPropertyChangedCallback? = null
): BaseObservable() {

    // error popup
    constructor(_isError: Boolean, _title: String, _message: String): this() {
        icon = R.drawable.common_google_signin_btn_icon_dark
        title = _title
        message = _message
        isError = _isError
    }

    var isRedTitle: Boolean
    init {
        if (callback != null) {
            addOnPropertyChangedCallback(callback)
        }

        // title color rule.
        isRedTitle = isError && hasMessage
    }

    val messageVisibility: Int get() = hasMessage.toVisibility()
    val positiveLabelVisibility: Int get() = hasPositive.toVisibility()
    val negativeLabelVisibility: Int get() = hasNegative.toVisibility()

    @Bindable
    var isPositiveClicked: Boolean = false
    @Bindable
    var isNegativeClicked: Boolean = false
    @Bindable
    var isCanceled: Boolean = false
    @Bindable
    var isDismissing: Boolean = false


    fun clickPositive(@Suppress("UNUSED_PARAMETER") view: View?) {
        isPositiveClicked = true
        notifyPropertyChanged(BR.isPositiveClicked)
        isPositiveClicked = false
        askDismiss()
    }

    fun clickNegative(@Suppress("UNUSED_PARAMETER") view: View?) {
        isNegativeClicked = true
        notifyPropertyChanged(BR.isNegativeClicked)
        isNegativeClicked = false
        askDismiss()
    }

    fun onCancel() {
        isCanceled = true
        notifyPropertyChanged(BR.isCanceled)
        isCanceled = false
    }

    fun askDismiss() {
        isDismissing = true
        notifyPropertyChanged(BR.isDismissing)
        isDismissing = false
    }
}
