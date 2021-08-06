package com.glass.mouher.ui.store.home.products.proudctDetail

import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.domain.entities.ProductUI
import com.glass.domain.entities.ScreenTopInformationUI
import com.glass.domain.usecases.cart.ICartUseCase
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.mouher.App.Companion.context
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ProductDetailViewModel(
    private val cartUseCase: ICartUseCase,
    private val productUseCase: IProductUseCase
): BaseViewModel(), ClickHandler<AProductDetailViewModel> {

    private var productId = 1

    private var storeId = 1

    @Bindable
    var urlImageTop: String = ""

    @Bindable
    var titleTop: String = ""

    @Bindable
    var subTitleTop: String = ""

    @Bindable
    var productName: String = ""

    @Bindable
    var productDescription: String = ""

    @Bindable
    var productRating: Int = 0

    @Bindable
    var currentPrice: String = "$"

    @Bindable
    var oldPrice: String = "$"

    @Bindable
    var showPopRating: Unit? = null

    @Bindable
    var openScreenReviews: Unit? = null

    @Bindable
    var miniSelected: String? = null

    @Bindable
    var itemsRelatedProducts = listOf<ProductUI>()

    @Bindable
    var onBack: Unit? = null


    @Bindable
    var items: List<AProductDetailViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }


    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }



    fun initialize(id: Int?, _storeId: Int?){
        productId = id ?: 1
        storeId = _storeId ?: 1
    }


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        progressVisible = true

        addDisposable(productUseCase.triggerToGetFullProduct(storeId, productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .flatMap {
                return@flatMap productUseCase.getProductUI()
            }

            .flatMap {
                onResponseProductUI(it)
                return@flatMap productUseCase.getTopScreenInformation()
            }

            .flatMap {
                onTopInformationResponse(it)
                return@flatMap productUseCase.getRelatedProductsByProduct()
            }

            .subscribe(this::onRelatedProductsListResponse, this::onError))
    }


    private fun onResponseProductUI(product: ProductUI){
        val miniPhotosList = mutableListOf<String?>().apply {
            add(product.sidePhoto1)
            add(product.sidePhoto2)
            add(product.sidePhoto3)
            add(product.sidePhoto4)
            add(product.sidePhoto5)
        }

        val viewModels = mutableListOf<AProductDetailViewModel>()

        miniPhotosList.forEach {
            context?.let{ c->
                val viewModel = ProductDetailItemMiniViewModel(context = c, image = it ?: "")
                viewModels.add(viewModel)
            }
        }

        items = viewModels

        // load first image on photoView
        if(items.isNotEmpty()){
            miniSelected = miniPhotosList[0]
            notifyPropertyChanged(BR.miniSelected)
        }

        // Product information
        productName = product.name ?: ""
        productDescription = product.description ?: ""
        productRating = product.rating ?: 0
        currentPrice = product.currentPrice ?: "$"
        oldPrice = product.oldPrice ?: "$"

        notifyPropertyChanged(BR.productName)
        notifyPropertyChanged(BR.productDescription)
        notifyPropertyChanged(BR.productRating)
        notifyPropertyChanged(BR.currentPrice)
        notifyPropertyChanged(BR.oldPrice)

        progressVisible = false
    }


    private fun onTopInformationResponse(topInformation: ScreenTopInformationUI){
        urlImageTop = topInformation.urlImageTop ?: ""
        titleTop = topInformation.titleTop ?: ""
        subTitleTop = topInformation.subTitleTop ?: ""
        progressVisible = false

        notifyPropertyChanged(BR.urlImageTop)
        notifyPropertyChanged(BR.titleTop)
        notifyPropertyChanged(BR.subTitleTop)
    }


    private fun onRelatedProductsListResponse(list: List<ProductUI>){
        itemsRelatedProducts = list
        notifyPropertyChanged(BR.itemsRelatedProducts)
    }


    fun onAddProductToCartClicked(@Suppress("UNUSED_PARAMETER") view: View){
        cartUseCase.setProductOnCart(Item(name = productName, description = "Descripcion: $productDescription"))
    }


    fun onAddRatingClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.showPopRating)
    }


    fun onReviewsClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.openScreenReviews)
    }


    private fun onError(t: Throwable?){
        Log.e("--", t?.localizedMessage.toString())
    }


    fun onBackClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.onBack)
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