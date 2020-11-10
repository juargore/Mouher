package com.glass.mouher.ui.store.home.products.proudctDetail

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.domain.entities.ProductUI
import com.glass.domain.usecases.cart.CartUseCase
import com.glass.domain.usecases.cart.ICartUseCase
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.ui.common.completeUrlForImage
import com.glass.mouher.ui.common.completeUrlForImageOnStore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ProductDetailViewModel(
    private val context: Context,
    private val cartUseCase: ICartUseCase,
    private val productUseCase: IProductUseCase
): BaseViewModel(), ClickHandler<AProductDetailViewModel> {

    private var productId = ""
    private var storeId = "1"

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
    var description = ""


    @Bindable
    var items: List<AProductDetailViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }


    fun initialize(id: String?){
        productId = id ?: ""
    }


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)


        addDisposable( productUseCase.getProductUI(productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponseProductUI, this::onError))

        val newRelatedProductsList = mutableListOf<Item>()
        newRelatedProductsList.add(Item(name = "Jogging", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1316/540/050/1316540050_4_1_8.jpg?t=1582651694009&imwidth=375"))
        newRelatedProductsList.add(Item(name = "Botín", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1059/640/040/1059640040_4_1_8.jpg?t=1594808755920&imwidth=375"))
        newRelatedProductsList.add(Item(name = "Sandalia", imageUrl = "https://static.pullandbear.net/2/photos//2020/I/1/1/p/1608/640/040/1608640040_4_1_8.jpg?t=1593175295433&imwidth=375"))

        itemsRelatedProducts = newRelatedProductsList
        notifyPropertyChanged(BR.itemsRelatedProducts)
    }

    private fun onResponseProductUI(product: ProductUI){
        val storesList = mutableListOf<String?>()

        storesList.add(completeUrlForImageOnStore(product.sidePhoto1, storeId))
        storesList.add(completeUrlForImageOnStore(product.sidePhoto2, storeId))
        storesList.add(completeUrlForImageOnStore(product.sidePhoto3, storeId))
        storesList.add(completeUrlForImageOnStore(product.sidePhoto4, storeId))
        storesList.add(completeUrlForImageOnStore(product.sidePhoto5, storeId))

        val viewModels = mutableListOf<AProductDetailViewModel>()

        storesList.forEach {
            val viewModel = ProductDetailItemMiniViewModel(context = context, image = it ?: "")
            viewModels.add(viewModel)
        }

        items = viewModels

        // load first image on photoView
        if(items.isNotEmpty()){
            miniSelected = storesList[0]
            notifyPropertyChanged(BR.miniSelected)
        }


        // description
        description = product.description ?: ""
        notifyPropertyChanged(BR.description)
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

    private fun onError(t: Throwable?){

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