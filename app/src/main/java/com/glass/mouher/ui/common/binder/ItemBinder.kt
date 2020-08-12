package com.glass.mouher.ui.common.binder

interface ItemBinder<Model> {
    fun getLayoutRes(model: Model): Int
    fun getBindingVariable(model: Model): Int
}