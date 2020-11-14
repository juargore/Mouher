package com.glass.mouher.ui.store.home.products

import android.content.Context
import com.glass.domain.entities.ProductUI

class ProductsItemViewModel(
    private val context: Context,
    product: ProductUI
): AProductsViewModel() {

    val id = product.id

    val name = product.name

    val imageUrl = product.sidePhoto1

    val description = product.description
}