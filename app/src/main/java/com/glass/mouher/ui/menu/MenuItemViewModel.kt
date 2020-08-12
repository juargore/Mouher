package com.glass.mouher.ui.menu

import android.content.Context
import com.glass.domain.entities.Item

class MenuItemViewModel(
    private val context: Context,
    private val menu: Item
): AMenuViewModel() {

    val name = menu.name

    val icon = menu.icon

    val imageUrl = menu.imageUrl

    val description = menu.description
}