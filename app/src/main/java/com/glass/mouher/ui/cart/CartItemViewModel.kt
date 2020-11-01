package com.glass.mouher.ui.cart

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.glass.domain.entities.Item

class CartItemViewModel(
    private val context: Context,
    private val menu: Item
): ACartListViewModel() {

    @Bindable
    var deleteClicked: Unit? = null

    val name = menu.name

    val icon = menu.icon

    val imageUrl = menu.imageUrl

    val description = "$ ${menu.description}"

    fun onDeleteClicked(v: View){
        //(v.parent as? SwipeRevealLayout)?.close(true)
        notifyPropertyChanged(BR.deleteClicked)
    }

}