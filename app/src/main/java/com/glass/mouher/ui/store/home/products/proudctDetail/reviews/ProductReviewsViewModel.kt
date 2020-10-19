package com.glass.mouher.ui.store.home.products.proudctDetail.reviews

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel

class ProductReviewsViewModel(
    private val context: Context
): BaseViewModel() {

    @Bindable
    var items: List<AProductReviewViewModel> = listOf()


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val storesList = mutableListOf<Item>()
        storesList.add(Item(name = "Admin", description = "Genial y barato"))
        storesList.add(Item(name = "José Díaz", description = "Me gustó mucho el producto"))
        storesList.add(Item(name = "Carlos Macías", description = "Gran producto para compartir en familia. Gran producto para compartir en familia. Gran producto para compartir en familia"))
        storesList.add(Item(name = "César Hernández", description = "Otro comentario corto"))
        storesList.add(Item(name = "Fernando N.", description = "Comentario largo. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."))

        val viewModels = mutableListOf<AProductReviewViewModel>()

        storesList.forEach {
            val viewModel = ProductReviewItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        items = viewModels
        notifyPropertyChanged(BR.items)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        items = listOf()
        onCleared()
    }
}