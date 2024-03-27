package com.ocean.mouher.ui.common

import androidx.databinding.Observable

interface PropertyListener {
    fun onPropertyChanged(sender: Observable?, propertyId: Int)
}

fun propertyChangedCallback(listener: PropertyListener) =
    object: Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            listener.onPropertyChanged(sender, propertyId)
        }
    }

fun propertyChangedCallback(listener: (Observable?, Int)-> Unit) = object: Observable.OnPropertyChangedCallback() {
    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
        listener(sender, propertyId)
    }
}