package com.glass.mouher.ui.mall.home.stores

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel

class StoresViewModel(
    private val context: Context
): BaseViewModel() {

    @Bindable
    var zoneName: String? = null

    @Bindable
    var items: List<AStoresViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }


    fun initialize(zoneName: String){
        this.zoneName = zoneName
        notifyPropertyChanged(BR.zoneName)
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val storesList = mutableListOf<Item>()
        storesList.add(Item(name = "Burger King", imageUrl = "https://live.mrf.io/statics/i/ps/www.muycomputer.com/wp-content/uploads/2018/01/Burger-King.jpg?width=1200&enable=upscale"))
        storesList.add(Item(name = "P.F. Chang's", imageUrl = "https://cdn.forbes.co/2020/07/PF-Chang-1280x720-1.jpg"))
        storesList.add(Item(name = "Italiannis", imageUrl = "https://www.elsiglodetorreon.com.mx/m/i/2011/06/299801.jpeg"))
        storesList.add(Item(name = "McDonalds", imageUrl = "https://d500.epimg.net/cincodias/imagenes/2015/01/30/franquicias/1422630557_593175_1422630724_noticia_normal.jpg"))
        storesList.add(Item(name = "Dunkin' Donuts", imageUrl = "https://s3.amazonaws.com/cms.ipressroom.com/285/files/20180/5a5e0f4b2cfac27fd8014ff2_Next+Generation+Concept+Store/Next+Generation+Concept+Store_fdd8eee5-2117-466a-8355-7450d7a09b04-prv.jpg"))
        storesList.add(Item(name = "Nutrisa", imageUrl = "https://imagenes.20minutos.com.mx/files/image_990_v1/uploads/imagenes/2019/06/19/6802.jpg"))

        val viewModels = mutableListOf<AStoresViewModel>()

        storesList.forEach {
            val viewModel = StoreListItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        items = viewModels
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }
}