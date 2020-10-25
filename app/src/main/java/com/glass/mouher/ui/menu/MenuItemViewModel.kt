package com.glass.mouher.ui.menu

import android.content.Context
import com.glass.domain.entities.Item
import com.glass.domain.entities.ZoneUI

class MenuItemViewModel(
    private val context: Context,
    private val menu: ZoneUI
): AMenuViewModel() {

    val id = menu.id

    val name = menu.name

    val zoneUI = menu
}