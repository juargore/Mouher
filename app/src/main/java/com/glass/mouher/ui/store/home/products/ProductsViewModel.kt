@file:Suppress("UNUSED_PARAMETER")

package com.glass.mouher.ui.store.home.products

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.ProductUI
import com.glass.domain.entities.ScreenTopInformationUI
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

class ProductsViewModel(
    private val productUseCase: IProductUseCase
): BaseViewModel(), ClickHandler<AProductsViewModel> {

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    var storeId: Int = 1

    private var categoryId = 1

    var productId: Int = 1


    @Bindable
    var urlImageTop = ""

    @Bindable
    var titleTop = ""

    @Bindable
    var subTitleTop = ""

    @Bindable
    var detailScreen: Unit? = null

    @Bindable
    var onBack: Unit? = null

    @Bindable
    var categoryName: String? = null

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



    fun initialize(c: Context, catId: Int?, stoId: Int?, catName: String?){
        context = c
        categoryId = catId ?: 1
        storeId = stoId ?: 1
        categoryName = catName ?: ""
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        progressVisible = true
        addDisposable(productUseCase.getProductListByCategory(storeId, categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .flatMap {
                onProductListResponse(it)
                return@flatMap productUseCase.getTopScreenInformation()
            }

            .subscribe(this::onTopInformationResponse, this::onError))
    }

    private fun onProductListResponse(storesList: List<ProductUI>){
        val viewModels = mutableListOf<AProductsViewModel>()

        storesList.forEach {
            val viewModel = ProductsItemViewModel(context = context, product = it)
            viewModels.add(viewModel)
        }

        items = viewModels
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

    fun onBackClicked(view: View){
        notifyPropertyChanged(BR.onBack)
    }

    private fun onError(t: Throwable?){
        progressVisible = false
        context.toast(t?.cause.toString())
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

    override fun onClick(viewModel: AProductsViewModel) {
        if(viewModel is ProductsItemViewModel){
            productId = viewModel.id ?: 1
            notifyPropertyChanged(BR.detailScreen)
        }
    }
}