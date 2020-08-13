package com.glass.mouher.ui.store.home.products

import android.content.Context
import com.glass.domain.entities.Item

class ProductsItemViewModel(
    private val context: Context,
    private val menu: Item
): AProductsViewModel() {

    val name = menu.name

    val icon = menu.icon

    val imageUrl = menu.imageUrl

    val description = menu.description
}