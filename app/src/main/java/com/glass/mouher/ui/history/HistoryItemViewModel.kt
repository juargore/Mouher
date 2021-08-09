package com.glass.mouher.ui.history

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import com.glass.domain.entities.Item
import androidx.databinding.library.baseAdapters.BR
import com.chauthai.swipereveallayout.SwipeRevealLayout

class HistoryItemViewModel(
    private val context: Context,
    private val menu: Item
): AHistoryListViewModel() {

    @Bindable
    var deleteClicked: Unit? = null

    val name = menu.name

    val imageUrl = menu.imageUrl

    val description = menu.description

    fun onDeleteClicked(v: View){
        (v.parent as? SwipeRevealLayout)?.close(true)

        notifyPropertyChanged(BR.deleteClicked)
    }

}