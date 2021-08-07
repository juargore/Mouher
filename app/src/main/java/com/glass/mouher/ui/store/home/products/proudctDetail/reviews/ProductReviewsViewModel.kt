package com.glass.mouher.ui.store.home.products.proudctDetail.reviews

import android.os.Handler
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.ResponseUI
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

    private var productId = 1

    private var storeId = 1

    @Bindable
    var items: List<AProductReviewViewModel> = listOf()

    @Bindable
    var backClicked: Unit? = null

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


    fun initialize(id: Int?, storId: Int?){
        productId = id ?: 1
        storeId = storId ?: 1
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
        startGettingInformation()
    }

    private fun startGettingInformation(){
        addDisposable(productUseCase.getReviewsForProduct(storeId, productId)
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
        error = ResponseUI(hasErrors = true, message = t?.message)
    }

    fun onBackClicked(@Suppress("UNUSED_PARAMETER") v: View){
        notifyPropertyChanged(BR.backClicked)
    }

    fun onAddReviewButtonClicked(@Suppress("UNUSED_PARAMETER") v: View){
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

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        items = listOf()
        onCleared()
    }
}