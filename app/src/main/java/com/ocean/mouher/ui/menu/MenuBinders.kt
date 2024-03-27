package com.ocean.mouher.ui.menu

import com.ocean.mouher.ui.common.binder.ConditionalDataBinder

class MenuItemBinder(bindingVariable: Int, layoutid: Int):
    ConditionalDataBinder<AMenuViewModel>(bindingVariable, layoutid){

    override fun canHandle(model: AMenuViewModel): Boolean {
        return model is MenuItemViewModel
    }
}