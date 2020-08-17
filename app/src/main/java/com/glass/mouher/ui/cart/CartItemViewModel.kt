package com.glass.mouher.ui.cart

import android.content.Context
import com.glass.domain.entities.Item

class CartItemViewModel(
    private val context: Context,
    private val menu: Item
): ACartListViewModel() {

    val name = menu.name

    val icon = menu.icon

    val imageUrl = menu.imageUrl

    val description = "$ ${menu.description}"

}