package com.glass.mouher.ui.store.home.products.proudctDetail.reviews

import com.glass.mouher.ui.common.binder.ConditionalDataBinder

class ProductsReviewsItemBinder(bindingVariable: Int, layoutId: Int):
        ConditionalDataBinder<AProductReviewViewModel>(bindingVariable, layoutId){
    override fun canHandle(model: AProductReviewViewModel): Boolean {
        return model is ProductReviewItemViewModel
    }

}