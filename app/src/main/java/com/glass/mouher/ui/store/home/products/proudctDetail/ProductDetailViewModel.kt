package com.glass.mouher.ui.store.home.products.proudctDetail

import android.os.Handler
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.domain.entities.ProductUI
import com.glass.domain.entities.ResponseUI
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

    var productId = 1

    var storeId = 1

    var productType = 1

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
    var currentPrice: Double = 0.0

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


    // ----- Classification values here ------ //
    @Bindable
    var titleClassification1: String? = null

    @Bindable
    var listClassification1AsString: String? = null

    @Bindable
    var listClassification1: List<String>? = listOf()

    var valueClassificationSelected1: String? = null

    @Bindable
    var titleClassification2: String? = null

    @Bindable
    var listClassification2AsString: String? = null

    @Bindable
    var listClassification2: List<String>? = listOf()

    var valueClassificationSelected2: String? = null

    @Bindable
    var titleClassification3: String? = null

    @Bindable
    var listClassification3AsString: String? = null

    @Bindable
    var listClassification3: List<String>? = listOf()

    var valueClassificationSelected3: String? = null

    @Bindable
    var titleClassificationQty: String? = null

    @Bindable
    var listClassificationQty: List<String>? = listOf()

    var valueClassificationSelectedQuantity: String? = null

    // ------- End classification values ------ //


    @Bindable
    var items: List<AProductDetailViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }


    @Bindable
    var error = ResponseUI()
        set(value) {
            field = value
            notifyPropertyChanged(BR.error)
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
        startGettingInformation()
    }

    private fun startGettingInformation(){
        progressVisible = true

        addDisposable(productUseCase.triggerToGetFullProduct(storeId, productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .flatMap {
                return@flatMap productUseCase.getProductUI()
            }

            .flatMap {
                onResponseProductUI(it)
                return@flatMap productUseCase.getTopScreenInformation(fromDetail = true)
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
        productDescription = product.fullDescription ?: ""
        productRating = product.rating ?: 0
        currentPrice = product.currentPrice?.toDouble() ?: 0.0
        productType = product.productType ?: 1

        product.oldPrice?.let{
            oldPrice = "$ $it"
            notifyPropertyChanged(BR.oldPrice)
        }

        notifyPropertyChanged(BR.productName)
        notifyPropertyChanged(BR.productDescription)
        notifyPropertyChanged(BR.productRating)
        notifyPropertyChanged(BR.currentPrice)


        // Classification section here
        titleClassification1 = product.classificationTitle1
        titleClassification2 = product.classificationTitle2
        titleClassification3 = product.classificationTitle3
        titleClassificationQty = "(${product.classificationValuesQty} en existencia)"

        notifyPropertyChanged(BR.titleClassification1)
        notifyPropertyChanged(BR.titleClassification2)
        notifyPropertyChanged(BR.titleClassification3)
        notifyPropertyChanged(BR.titleClassificationQty)

        listClassification1 = product.classificationValues1
        listClassification1AsString = product.classificationValues1AsString
        listClassification2 = product.classificationValues2
        listClassification2AsString = product.classificationValues2AsString
        listClassification3 = product.classificationValues3
        listClassification3AsString = product.classificationValues3AsString

        notifyPropertyChanged(BR.listClassification1)
        notifyPropertyChanged(BR.listClassification1AsString)
        notifyPropertyChanged(BR.listClassification2)
        notifyPropertyChanged(BR.listClassification2AsString)
        notifyPropertyChanged(BR.listClassification3)
        notifyPropertyChanged(BR.listClassification3AsString)

        val total = product.classificationValuesQty

        total?.let{ t->
            val mList = mutableListOf<String>()

            for(i in 1 until if(t > 10) 11 else t+1){
                mList.add("$i")
            }

            listClassificationQty = mList
            notifyPropertyChanged(BR.listClassificationQty)
        }
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
        progressVisible = false

        itemsRelatedProducts = list
        notifyPropertyChanged(BR.itemsRelatedProducts)
    }


    fun onAddProductToCartClicked(@Suppress("UNUSED_PARAMETER") view: View){
        var value = valueClassificationSelected1

        if(!valueClassificationSelected2.isNullOrBlank()){
            value = "$value, $valueClassificationSelected2"
        }

        if(!valueClassificationSelected3.isNullOrBlank()){
            value = "$value, $valueClassificationSelected3"
        }

        cartUseCase.setProductOnCart(
            Item(id = productId,
                name = productName,
                description = productDescription,
                price = currentPrice,
                imageUrl = miniSelected,
                quantity = valueClassificationSelectedQuantity?.toInt() ?: 1,
                valueClassification = value,
                storeId = storeId,
                productType = productType
            ))
    }


    fun onAddRatingClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.showPopRating)
    }


    fun onAddRatingFromPopupClicked(name: String, email: String, comment: String, rating: Float){
        progressVisible = true

        addDisposable(productUseCase.saveNewReviewForProduct(
            storeId = storeId,
            productId = productId,
            userName = name,
            userEmail = email,
            userComment = comment,
            userRating = rating
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ responseUI->
                progressVisible = false

                // inform result on UI
                error = ResponseUI(
                    hasErrors = responseUI.hasErrors,
                    message = responseUI.message)

                Handler().postDelayed({
                    startGettingInformation() }, 1000)
            }, this::onError))
    }


    fun onReviewsClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.openScreenReviews)
    }


    private fun onError(t: Throwable?){
        progressVisible = false
        error = ResponseUI(hasErrors = true, message = t?.message)
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