package com.ocean.mouher.ui.history

import com.ocean.mouher.ui.common.binder.ConditionalDataBinder

class HistoryItemBinder(bindingVariable: Int, layoutId: Int):
        ConditionalDataBinder<AHistoryListViewModel>(bindingVariable, layoutId){
    override fun canHandle(model: AHistoryListViewModel): Boolean {
        return model is HistoryItemViewModel
    }

}