package com.glass.mouher.ui.store.home

import android.content.Context
import com.glass.domain.entities.CategoryUI

class StoreCategoryItemViewModel(
    private val context: Context,
    category: CategoryUI
): AStoreCategoryViewModel() {

    val id = category.id

    val name = category.name

    val description = category.description

    val imageUrl = category.imageUrl
}