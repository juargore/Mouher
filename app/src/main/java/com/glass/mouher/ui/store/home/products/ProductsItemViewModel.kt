package com.glass.mouher.ui.store.home.products

import android.content.Context
import android.view.View
import com.glass.domain.entities.ProductUI

class ProductsItemViewModel(
    private val context: Context,
    product: ProductUI
): AProductsViewModel() {

    val id = product.id

    val name = product.name

    val imageUrl = product.sidePhoto1

    val ratingStar = product.rating

    val description = product.description

    val currentPrice = product.currentPrice

    val oldPriceVisible = if(product.oldPrice.isNullOrBlank()) View.GONE else View.VISIBLE

    val oldPrice = product.oldPrice

    val discountVisible = if(product.discount.isNullOrBlank()) View.GONE else View.VISIBLE

    val discount = product.discount

    val middleLine = true
}