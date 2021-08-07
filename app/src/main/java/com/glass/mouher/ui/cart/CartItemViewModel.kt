package com.glass.mouher.ui.cart

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item

class CartItemViewModel(
    private val context: Context,
    item: Item
): ACartListViewModel() {

    @Bindable
    var deleteClicked: Unit? = null

    val id = item.id

    val name = item.name

    val quantity = item.quantity.toString()

    val description = item.valueClassification

    val price = item.price

    val icon = item.icon

    val imageUrl = item.imageUrl

    fun onDeleteClicked(v: View){
        //(v.parent as? SwipeRevealLayout)?.close(true)
        notifyPropertyChanged(BR.deleteClicked)
    }

}