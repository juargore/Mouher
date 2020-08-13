package com.glass.mouher.ui.store.home

import com.glass.mouher.ui.common.binder.ConditionalDataBinder


class StoreCategoryItemBinder(bindingVariable: Int, layoutId: Int):
        ConditionalDataBinder<AStoreCategoryViewModel>(bindingVariable, layoutId){
    override fun canHandle(model: AStoreCategoryViewModel): Boolean {
        return model is StoreCategoryItemViewModel
    }
}