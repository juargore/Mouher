package com.glass.mouher.ui.store.home

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler

class HomeStoreViewModel(
    private val context: Context
): BaseViewModel(), ClickHandler<AStoreCategoryViewModel> {

    @Bindable
    val urlTop = "https://mockuptree.com/wp-content/uploads/edd/2019/07/free-shopping-center-banner-mockups.jpg"

    @Bindable
    var onClick: Unit? = null

    @Bindable
    var items: List<AStoreCategoryViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val categoriesList = mutableListOf<Item>()
        categoriesList.add(Item(name = "Burger King", imageUrl = "https://live.mrf.io/statics/i/ps/www.muycomputer.com/wp-content/uploads/2018/01/Burger-King.jpg?width=1200&enable=upscale"))
        categoriesList.add(Item(name = "P.F. Chang's", imageUrl = "https://cdn.forbes.co/2020/07/PF-Chang-1280x720-1.jpg"))
        categoriesList.add(Item(name = "Italiannis", imageUrl = "https://www.elsiglodetorreon.com.mx/m/i/2011/06/299801.jpeg"))
        categoriesList.add(Item(name = "McDonalds", imageUrl = "https://d500.epimg.net/cincodias/imagenes/2015/01/30/franquicias/1422630557_593175_1422630724_noticia_normal.jpg"))

        val viewModels = mutableListOf<AStoreCategoryViewModel>()

        categoriesList.forEach {
            val viewModel = StoreCategoryItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        items = viewModels
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

    override fun onClick(viewModel: AStoreCategoryViewModel) {
        if(viewModel is StoreCategoryItemViewModel){
            notifyPropertyChanged(BR.onClick)
        }
    }
}