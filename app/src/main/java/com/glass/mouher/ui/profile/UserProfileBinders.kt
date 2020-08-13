package com.glass.mouher.ui.profile

import com.glass.mouher.ui.common.binder.ConditionalDataBinder

class UserProfileItemBinder(bindingVariable: Int, layoutId: Int):
        ConditionalDataBinder<AUserProfileViewModel>(bindingVariable, layoutId){
    override fun canHandle(model: AUserProfileViewModel): Boolean {
        return model is UserProfileItemViewModel
    }

}