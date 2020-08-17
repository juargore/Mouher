package com.glass.mouher.ui.cart

import com.glass.mouher.ui.common.binder.ConditionalDataBinder

class CartItemBinder(bindingVariable: Int, layoutId: Int):
        ConditionalDataBinder<ACartListViewModel>(bindingVariable, layoutId){
    override fun canHandle(model: ACartListViewModel): Boolean {
        return model is CartItemViewModel
    }

}