package com.glass.mouher.ui.cart

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.domain.usecases.cart.CartUseCase
import com.glass.domain.usecases.cart.ICartUseCase
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.ui.store.home.products.AProductsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CartViewModel(
    private val context: Context,
    private val cartUseCase: ICartUseCase
): BaseViewModel(), ClickHandler<ACartListViewModel> {

    @Bindable
    var onPopClicked: Unit? = null

    @Bindable
    var onBackClicked: Unit? = null

    @Bindable
    var items: List<ACartListViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        addDisposable(
            cartUseCase.getTotalProductsOnDb()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { mList ->
                    val viewModels = mutableListOf<ACartListViewModel>()

                    mList.forEach {
                        val viewModel = CartItemViewModel(context = context, menu = it)
                        viewModels.add(viewModel)
                    }

                    items = viewModels
                }
        )
    }

    fun onPopClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.onPopClicked)
    }

    fun onBackClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.onBackClicked)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

    override fun onClick(viewModel: ACartListViewModel) {
        if(viewModel is CartItemViewModel){
            cartUseCase.deleteProductOnCart(viewModel.name!!)
        }
    }
}