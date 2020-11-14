@file:Suppress("UNUSED_PARAMETER")

package com.glass.mouher.ui.store.home.products

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.ProductUI
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.ui.common.completeUrlForImageOnStore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

class ProductsViewModel(
    private val context: Context,
    private val productUseCase: IProductUseCase
): BaseViewModel(), ClickHandler<AProductsViewModel> {

    private var categoryId = ""

    @Bindable
    var storeId = ""

    @Bindable
    var urlTop = "https://static.zara.net/photos//mkt/spots/aw20-north-shoes-bags-woman/subhome-xmedia-33//landscape_0.jpg?ts=1597317424891&imwidth=1366"

    @Bindable
    var productId: String? = null

    @Bindable
    var detailScreen: Unit? = null

    @Bindable
    var onBack: Unit? = null

    @Bindable
    var categoryName = "Zapatos"

    @Bindable
    var items: List<AProductsViewModel> = listOf()
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


    fun initialize(_categoryId: String?, _storeId: String?){
        categoryId = _categoryId ?: ""
        storeId = _storeId ?: ""
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        progressVisible = true
        addDisposable(productUseCase.getProductListByCategory(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onProductListResponse, this::onError))
    }

    private fun onProductListResponse(storesList: List<ProductUI>){
        val viewModels = mutableListOf<AProductsViewModel>()

        storesList.forEach {
            it.sidePhoto1 = completeUrlForImageOnStore(it.sidePhoto1, storeId)
            val viewModel = ProductsItemViewModel(context = context, product = it)
            viewModels.add(viewModel)
        }

        progressVisible = false
        items = viewModels
    }

    fun onBackClicked(view: View){
        notifyPropertyChanged(BR.onBack)
    }

    private fun onError(t: Throwable?){
        context.toast(t?.cause.toString())
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

    override fun onClick(viewModel: AProductsViewModel) {
        if(viewModel is ProductsItemViewModel){
            productId = viewModel.id
            notifyPropertyChanged(BR.detailScreen)
        }
    }
}