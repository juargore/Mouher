package com.glass.mouher.ui.store.home.products.proudctDetail

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler


class ProductDetailViewModel(
    private val context: Context
): BaseViewModel(), ClickHandler<AProductDetailViewModel> {

    @Bindable
    var miniSelected: String? = null

    @Bindable
    var items: List<AProductDetailViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val storesList = mutableListOf<Item>()
        storesList.add(Item(name = "Botín suela track", imageUrl = "https://www.freepngimg.com/thumb/shoes/28084-5-sneaker-transparent-image-thumb.png"))
        storesList.add(Item(name = "Sandalia tacón tiras", imageUrl = "https://i.pinimg.com/originals/1d/49/ba/1d49bad547fd40681dbbd58e827675dd.jpg"))
        storesList.add(Item(name = "Tenis picado blanco", imageUrl = "https://purepng.com/public/uploads/large/sandals-bxq.png"))
        storesList.add(Item(name = "Pala dorada trenzada", imageUrl = "https://5.imimg.com/data5/XX/CQ/MY-14472477/balujas-men-formal-1100-cherry-shoe-500x500.png"))

        val viewModels = mutableListOf<AProductDetailViewModel>()

        storesList.forEach {
            val viewModel = ProductDetailItemMiniViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        // load first image on photoView
        items = viewModels
        if(items.isNotEmpty()){
            miniSelected = storesList[0].imageUrl
            notifyPropertyChanged(BR.miniSelected)
        }
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        items = listOf()
        onCleared()
    }

    override fun onClick(viewModel: AProductDetailViewModel) {
        if(viewModel is ProductDetailItemMiniViewModel){
            miniSelected = viewModel.imageUrl
            notifyPropertyChanged(BR.miniSelected)
        }
    }
}