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
    val urlTop = "https://static.zara.net/photos//mkt/spots/ss20-north-welcome-back/subhome-xmedia-24//landscape_0.jpg?ts=1591378024003&imwidth=1366"

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
        categoriesList.add(Item(name = "Accesorios", description = "Complementa tu estilo", imageUrl = "https://static.zara.net/photos//mkt/spots/aw20-north-shoes-bags-woman/subhome-xmedia-33//landscape_0.jpg?ts=1597317424891&imwidth=1366"))
        categoriesList.add(Item(name = "Cremas", description = "Cuida tu piel", imageUrl = "https://static.pullandbear.net/2/static2/itxwebstandard/images/home/2020-07/31/1400/Newin_Woman.jpg?ver=20200813112500"))
        categoriesList.add(Item(name = "Hogar", description = "Todo para tu hogar", imageUrl = "https://static.zara.net/photos///2020/I/1/1/p/6660/510/040/3/w/1337/6660510040_9_1_1.jpg?ts=1597259304529"))
        categoriesList.add(Item(name = "Oficina", description = "Trabaja como te gusta", imageUrl = "https://static.zara.net/photos///rw-center/2020/I/0/1/p/1856/209/881/2/w/1337/1856209881_2_11_1.jpg?ts=1597061763237"))
        categoriesList.add(Item(name = "Accesorios", description = "Complementa tu estilo", imageUrl = "https://static.zara.net/photos//mkt/spots/aw20-north-shoes-bags-woman/subhome-xmedia-33//landscape_0.jpg?ts=1597317424891&imwidth=1366"))
        categoriesList.add(Item(name = "Cremas", description = "Cuida tu piel", imageUrl = "https://static.pullandbear.net/2/static2/itxwebstandard/images/home/2020-07/31/1400/Newin_Woman.jpg?ver=20200813112500"))

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