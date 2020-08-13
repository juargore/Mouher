package com.glass.mouher.ui.profile

import android.content.Context
import com.glass.domain.entities.Item

class UserProfileItemViewModel(
    private val context: Context,
    private val menu: Item
): AUserProfileViewModel() {

    val name = menu.name

    val icon = menu.icon

    val imageUrl = menu.imageUrl

    val description = menu.description
}