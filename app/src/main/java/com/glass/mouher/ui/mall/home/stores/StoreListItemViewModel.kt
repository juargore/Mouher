package com.glass.mouher.ui.mall.home.stores

import android.content.Context
import com.glass.domain.entities.Item

class StoreListItemViewModel(
    private val context: Context,
    private val menu: Item
): AStoresViewModel() {

    val name = menu.name

    val icon = menu.icon

    val imageUrl = menu.imageUrl

    val description = menu.description

}