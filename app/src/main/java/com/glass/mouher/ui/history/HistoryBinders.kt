package com.glass.mouher.ui.history

import com.glass.mouher.ui.common.binder.ConditionalDataBinder

class HistoryItemBinder(bindingVariable: Int, layoutId: Int):
        ConditionalDataBinder<AHistoryListViewModel>(bindingVariable, layoutId){
    override fun canHandle(model: AHistoryListViewModel): Boolean {
        return model is HistoryListItemViewModel
    }

}