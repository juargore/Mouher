package com.glass.mouher.ui.cart

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.mouher.ui.base.BaseViewModel

class CartViewModel(
    private val context: Context
): BaseViewModel() {

    @Bindable
    var onPopClicked: Unit? = null

    @Bindable
    var onBackClicked: Unit? = null

    @Bindable
    var items: List<ACartListViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val mList = mutableListOf<Item>()
        mList.add(Item(name = "Botín suela track", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1059/640/040/1059640040_4_1_8.jpg?t=1594808755920&imwidth=375", description = "100"))
        mList.add(Item(name = "Sandalia tacón tiras", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1608/640/040/1608640040_4_1_8.jpg?t=1593175295433&imwidth=375", description = "150"))
        mList.add(Item(name = "Tenis picado blanco y es otro ejemplo con más palabras y más palabras extra", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1224/540/001/1224540001_4_1_8.jpg?t=1585572071607&imwidth=375", description = "200"))
        mList.add(Item(name = "Pala dorada trenzada", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1562/540/091/1562540091_4_1_8.jpg?t=1583955278482&imwidth=375", description = "170"))
        mList.add(Item(name = "Sandala plataforma yute con texto largo", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1506/540/040/1506540040_4_1_8.jpg?t=1583336479068&imwidth=375", description = "220"))

        val viewModels = mutableListOf<ACartListViewModel>()

        mList.forEach {
            val viewModel = CartItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        items = viewModels
    }

    fun onPopClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.onPopClicked)
    }

    fun onBackClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.onBackClicked)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}