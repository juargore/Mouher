package com.glass.mouher.ui.common

import android.view.View

fun Boolean.toVisibility(): Int {
    return if (this) View.VISIBLE else View.GONE
}