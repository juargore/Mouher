package com.ocean.mouher.ui.store.home.products

import com.ocean.mouher.ui.common.binder.ConditionalDataBinder

class ProductsItemBinder(bindingVariable: Int, layoutId: Int):
        ConditionalDataBinder<AProductsViewModel>(bindingVariable, layoutId){
    override fun canHandle(model: AProductsViewModel): Boolean {
        return model is ProductsItemViewModel
    }
}