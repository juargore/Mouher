package com.glass.mouher.ui.store.home.products.proudctDetail

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.domain.usecases.cart.CartUseCase
import com.glass.domain.usecases.cart.ICartUseCase
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler


class ProductDetailViewModel(
    private val context: Context,
    private val cartUseCase: ICartUseCase
): BaseViewModel(), ClickHandler<AProductDetailViewModel> {

    @Bindable
    var urlTop = "https://static.zara.net/photos//mkt/spots/aw20-north-shoes-bags-woman/subhome-xmedia-33//landscape_0.jpg?ts=1597317424891&imwidth=1366"

    @Bindable
    var showPopRating: Unit? = null

    @Bindable
    var openScreenReviews: Unit? = null

    @Bindable
    var miniSelected: String? = null

    @Bindable
    var itemsRelatedProducts = mutableListOf<Item>()

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

        val newRelatedProductsList = mutableListOf<Item>()
        newRelatedProductsList.add(Item(name = "Jogging", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1316/540/050/1316540050_4_1_8.jpg?t=1582651694009&imwidth=375"))
        newRelatedProductsList.add(Item(name = "Botín", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1059/640/040/1059640040_4_1_8.jpg?t=1594808755920&imwidth=375"))
        newRelatedProductsList.add(Item(name = "Sandalia", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1608/640/040/1608640040_4_1_8.jpg?t=1593175295433&imwidth=375"))

        itemsRelatedProducts = newRelatedProductsList
        notifyPropertyChanged(BR.itemsRelatedProducts)
    }

    fun onAddProductToCartClicked(@Suppress("UNUSED_PARAMETER") view: View){
        cartUseCase.setProductOnCart(Item(name = getRandomString(), description = "Descripcion: ${getRandomString()}"))
    }

    fun getRandomString() : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..5)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun onAddRatingClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.showPopRating)
    }

    fun onReviewsClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.openScreenReviews)
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