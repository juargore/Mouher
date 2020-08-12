package com.glass.mouher.ui.common.binder

class CompositeItemBinder<T>(vararg conditionalDataBinders: ConditionalDataBinder<T>) :
    ItemBinder<T> {
    private val conditionalDataBinders: Array<ConditionalDataBinder<T>> = arrayOf(*conditionalDataBinders)

    override fun getLayoutRes(model: T): Int {
        for (i in conditionalDataBinders.indices) {
            val dataBinder = conditionalDataBinders[i]
            if (dataBinder.canHandle(model)) {
                return dataBinder.getLayoutRes(model)
            }
        }

        throw IllegalStateException()
    }

    override fun getBindingVariable(model: T): Int {
        for (i in conditionalDataBinders.indices) {
            val dataBinder = conditionalDataBinders[i]
            if (dataBinder.canHandle(model)) {
                return dataBinder.getBindingVariable(model)
            }
        }

        throw IllegalStateException()
    }
}
