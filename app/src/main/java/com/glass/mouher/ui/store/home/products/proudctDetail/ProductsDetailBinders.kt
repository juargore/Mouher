package com.glass.mouher.ui.store.home.products.proudctDetail

import com.glass.mouher.ui.common.binder.ConditionalDataBinder

class ProductsDetailItemMiniBinder(bindingVariable: Int, layoutId: Int):
        ConditionalDataBinder<AProductDetailViewModel>(bindingVariable, layoutId){
    override fun canHandle(model: AProductDetailViewModel): Boolean {
        return model is ProductDetailItemMiniViewModel
    }

}