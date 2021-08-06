package com.glass.mouher.ui.store.home.products.proudctDetail.reviews

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.ReviewUI
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.mouher.App.Companion.context
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductReviewsViewModel(
    private val productUseCase: IProductUseCase
): BaseViewModel() {

    @Bindable
    var items: List<AProductReviewViewModel> = listOf()

    @Bindable
    var backClicked: Unit? = null

    @Bindable
    var error: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.error)
        }

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        addDisposable(productUseCase.getReviewsForProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onReviewsResponse, this::onError))
    }

    private fun onReviewsResponse(list: List<ReviewUI>){
              val viewModels = mutableListOf<AProductReviewViewModel>()

        list.forEach {
            context?.let{ c->
                val viewModel = ProductReviewItemViewModel(context = c, review = it)
                viewModels.add(viewModel)
            }
        }

        items = viewModels
        notifyPropertyChanged(BR.items)
    }

    private fun onError(t: Throwable?){
        error = t?.localizedMessage.toString()
    }

    fun onBackClicked(@Suppress("UNUSED_PARAMETER") v: View){
        notifyPropertyChanged(BR.backClicked)
    }

    fun onAddReviewButtonClicked(@Suppress("UNUSED_PARAMETER") v: View){
        //TODO: Implement review here
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        items = listOf()
        onCleared()
    }
}