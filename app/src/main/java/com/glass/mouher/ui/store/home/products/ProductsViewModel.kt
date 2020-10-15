package com.glass.mouher.ui.store.home.products

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.ui.mall.home.stores.AStoresViewModel
import com.glass.mouher.ui.mall.home.stores.StoreItemViewModel

class ProductsViewModel(
    private val context: Context
): BaseViewModel(), ClickHandler<AProductsViewModel> {

    @Bindable
    var urlTop = "https://static.zara.net/photos//mkt/spots/aw20-north-shoes-bags-woman/subhome-xmedia-33//landscape_0.jpg?ts=1597317424891&imwidth=1366"

    @Bindable
    var productId: String? = null

    @Bindable
    var detailScreen: Unit? = null

    @Bindable
    var categoryName = "Zapatos"

    @Bindable
    var items: List<AProductsViewModel> = listOf()
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
        storesList.add(Item(name = "Sandala plataforma yute", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1506/540/040/1506540040_4_1_8.jpg?t=1583336479068&imwidth=375"))
        storesList.add(Item(name = "Tenis combinado pieza", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1309/640/009/1309640009_2_2_8.jpg?t=1591956118609&imwidth=375"))
        storesList.add(Item(name = "Tenis suela contraste", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1210/640/040/1210640040_4_1_8.jpg?t=1592314410960&imwidth=375"))
        storesList.add(Item(name = "Jogging rosa", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1316/540/050/1316540050_4_1_8.jpg?t=1582651694009&imwidth=375"))
        storesList.add(Item(name = "Botín suela track", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1059/640/040/1059640040_4_1_8.jpg?t=1594808755920&imwidth=375"))
        storesList.add(Item(name = "Sandalia tacón tiras", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1608/640/040/1608640040_4_1_8.jpg?t=1593175295433&imwidth=375"))
        storesList.add(Item(name = "Tenis picado blanco", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1224/540/001/1224540001_4_1_8.jpg?t=1585572071607&imwidth=375"))
        storesList.add(Item(name = "Pala dorada trenzada", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1562/540/091/1562540091_4_1_8.jpg?t=1583955278482&imwidth=375"))

        val viewModels = mutableListOf<AProductsViewModel>()

        storesList.forEach {
            val viewModel = ProductsItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        items = viewModels
    }

    fun onBackClicked(view: View){

    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        items = listOf()
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

    override fun onClick(viewModel: AProductsViewModel) {
        if(viewModel is ProductsItemViewModel){
            productId = viewModel.name
            notifyPropertyChanged(BR.detailScreen)
        }
    }
}