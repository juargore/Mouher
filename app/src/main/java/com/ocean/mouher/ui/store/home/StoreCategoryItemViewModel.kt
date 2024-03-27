package com.ocean.mouher.ui.store.home

import android.content.Context
import com.ocean.domain.entities.CategoryUI

class StoreCategoryItemViewModel(
    private val context: Context,
    category: CategoryUI
): AStoreCategoryViewModel() {

    val id = category.categoryId

    val name = category.name

    val description = category.description

    val imageUrl = category.imageUrl
}