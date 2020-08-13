package com.glass.mouher.ui.store.home.products

import com.glass.mouher.ui.common.binder.ConditionalDataBinder

class ProductsItemBinder(bindingVariable: Int, layoutId: Int):
        ConditionalDataBinder<AProductsViewModel>(bindingVariable, layoutId){
    override fun canHandle(model: AProductsViewModel): Boolean {
        return model is ProductsItemViewModel
    }
}