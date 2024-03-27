package com.ocean.mouher.ui.menu

import android.content.Context
import com.ocean.domain.entities.ZoneUI

class MenuItemViewModel(
    private val context: Context,
    private val menu: ZoneUI
): AMenuViewModel() {

    val id = menu.id

    val name = menu.name

    val zoneUI = menu
}