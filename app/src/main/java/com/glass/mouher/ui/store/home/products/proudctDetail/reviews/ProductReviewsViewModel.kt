package com.glass.mouher.ui.store.home.products.proudctDetail.reviews

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.domain.entities.ReviewUI
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductReviewsViewModel(
    private val context: Context,
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
            notifyPropertyChanged(androidx.databinding.library.baseAdapters.BR.progressVisible)
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        addDisposable(productUseCase.getReviewsForProduct("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onReviewsResponse, this::onError))
    }

    private fun onReviewsResponse(list: List<ReviewUI>){
              val viewModels = mutableListOf<AProductReviewViewModel>()

        list.forEach {
            val viewModel = ProductReviewItemViewModel(context = context, review = it)
            viewModels.add(viewModel)
        }

        items = viewModels
        notifyPropertyChanged(BR.items)
    }

    private fun onError(t: Throwable?){
        error = t?.localizedMessage.toString()
    }

    fun onBackClicked(v: View){
        notifyPropertyChanged(BR.backClicked)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        items = listOf()
        onCleared()
    }
}