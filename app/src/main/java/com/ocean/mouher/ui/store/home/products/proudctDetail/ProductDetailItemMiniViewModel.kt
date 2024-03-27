package com.ocean.mouher.ui.store.home.products.proudctDetail

import android.content.Context

class ProductDetailItemMiniViewModel(
    private val context: Context,
    private val image: String
): AProductDetailViewModel() {

    val imageUrl = image

}