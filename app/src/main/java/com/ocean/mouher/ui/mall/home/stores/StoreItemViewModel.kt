package com.ocean.mouher.ui.mall.home.stores

import android.content.Context
import com.ocean.domain.entities.StoreInZoneUI

class StoreItemViewModel(
    private val context: Context,
    private val store: StoreInZoneUI
): AStoresViewModel() {

    val id = store.id

    val name = store.name

    val imageUrl = store.urlImage

    val totalProducts = "(${store.totalProducts} productos)"

}