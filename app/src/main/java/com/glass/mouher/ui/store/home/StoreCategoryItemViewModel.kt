package com.glass.mouher.ui.store.home

import android.content.Context
import com.glass.domain.entities.Item

class StoreCategoryItemViewModel(
    private val context: Context,
    private val menu: Item
): AStoreCategoryViewModel() {

    val name = menu.name

    val icon = menu.icon

    val imageUrl = menu.imageUrl

    val description = menu.description

}