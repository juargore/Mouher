package com.ocean.mouher.ui.mall.home.stores

import com.ocean.mouher.ui.common.binder.ConditionalDataBinder

class StoresItemBinder(bindingVariable: Int, layoutId: Int):
        ConditionalDataBinder<AStoresViewModel>(bindingVariable, layoutId){
    override fun canHandle(model: AStoresViewModel): Boolean {
        return model is StoreItemViewModel
    }
}