package com.glass.mouher.ui.store.home.products.proudctDetail

import android.content.Context
import com.glass.domain.entities.ProductUI

class ProductDetailItemMiniViewModel(
    private val context: Context,
    private val image: String
): AProductDetailViewModel() {

    val imageUrl = image

}