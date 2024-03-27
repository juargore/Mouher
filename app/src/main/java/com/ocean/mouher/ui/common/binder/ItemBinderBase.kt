package com.ocean.mouher.ui.common.binder

open class ItemBinderBase<Model>(protected val bindingVariable: Int, protected val layoutId: Int)
    : ItemBinder<Model> {
    override fun getLayoutRes(model: Model): Int {
        return layoutId
    }

    override fun getBindingVariable(model: Model): Int {
        return bindingVariable
    }
}
