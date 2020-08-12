package com.glass.mouher.ui.history

import android.content.Context
import com.glass.domain.entities.Item

class HistoryListItemViewModel(
    private val context: Context,
    private val menu: Item
): AHistoryListViewModel() {

    val name = menu.name

    val icon = menu.icon

    val imageUrl = menu.imageUrl

    val description = menu.description

}