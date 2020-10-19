package com.glass.mouher.ui.store.home.products.proudctDetail.reviews

import android.content.Context
import com.glass.domain.entities.Item
import com.glass.mouher.ui.store.home.products.proudctDetail.AProductDetailViewModel

class ProductReviewItemViewModel(
    private val context: Context,
    private val menu: Item
): AProductReviewViewModel() {

    val name = "${menu.name} -"

    val icon = menu.icon

    val imageUrl = menu.imageUrl

    val description = menu.description
}