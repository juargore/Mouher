package com.glass.mouher.ui.store.home.products.proudctDetail

import android.content.Context
import com.glass.domain.entities.Item

class ProductDetailItemMiniViewModel(
    private val context: Context,
    private val menu: Item
): AProductDetailViewModel() {

    val name = menu.name

    val icon = menu.icon

    val imageUrl = menu.imageUrl

    val description = menu.description
}