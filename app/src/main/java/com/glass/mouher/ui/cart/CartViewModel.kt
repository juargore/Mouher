package com.glass.mouher.ui.cart

import android.os.Handler
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.domain.usecases.cart.ICartUseCase
import com.glass.mouher.App.Companion.context
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.ui.common.propertyChangedCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.math.RoundingMode

class CartViewModel(
    private val cartUseCase: ICartUseCase
): BaseViewModel(), ClickHandler<ACartListViewModel> {

    @Bindable
    var onRefreshScreen: Unit? = null

    @Bindable
    var onPopClicked: Unit? = null

    @Bindable
    var deleteItem: Int? = null

    @Bindable
    var totalAmount: Double = 0.0
        set(value){
            field = BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            notifyPropertyChanged(BR.totalAmount)
        }

    @Bindable
    var onBackClicked: Unit? = null

    @Bindable
    var items: List<ACartListViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    /**
     * @property itemPropertyChangedCallback listener to item actions
     */
    val itemPropertyChangedCallback =
            propertyChangedCallback { sender: Observable?, propertyId: Int ->
                if(sender is CartItemViewModel){
                    when(propertyId){
                        BR.deleteClicked -> {
                            deleteItem = sender.id
                            notifyPropertyChanged(BR.deleteItem)
                        }
                    }
                }
            }


    private var firstTime = true

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        if(firstTime){
            firstTime = false

            addDisposable(cartUseCase.getTotalProductsOnDb()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { onTotalProductsDbResponse(it) })
        }
    }


    private fun onTotalProductsDbResponse(list: List<Item>){
        val viewModels = mutableListOf<ACartListViewModel>()

        list.forEach {
            context?.let{ c->
                val viewModel = CartItemViewModel(context = c, item = it)
                viewModels.add(viewModel)

                totalAmount += (it.quantity!! * (it.price ?: 0.0))
            }
        }

        items = viewModels
    }


    fun onDeleteItemClicked(itemId: Int){
        cartUseCase.deleteProductOnCart(itemId)

        Handler().postDelayed({
            notifyPropertyChanged(BR.onRefreshScreen)
        }, 500)
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
        /*if(viewModel is CartItemViewModel){

        }*/
    }
}