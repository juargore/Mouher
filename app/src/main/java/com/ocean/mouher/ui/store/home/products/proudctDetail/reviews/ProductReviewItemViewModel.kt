package com.ocean.mouher.ui.store.home.products.proudctDetail.reviews

import android.content.Context
import com.ocean.domain.entities.ReviewUI

class ProductReviewItemViewModel(
    private val context: Context,
    private val review: ReviewUI
): AProductReviewViewModel() {

    val userName = review.userName

    val date = review.date

    val comment = review.review

    val rating = review.rating

    val urlImage = review.urlPhoto
}