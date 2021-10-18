@file:Suppress("UNUSED_PARAMETER")
package com.glass.mouher.ui.cart

import android.os.Handler
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.domain.entities.ResponsePaymentStatus
import com.glass.domain.usecases.cart.ICartUseCase
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.mouher.App.Companion.context
import com.glass.mouher.shared.General.getPaymentInfo
import com.glass.mouher.shared.General.getStoreShoppingInfo
import com.glass.mouher.shared.General.getUserId
import com.glass.mouher.shared.General.getUserName
import com.glass.mouher.shared.General.getUserSignedIn
import com.glass.mouher.shared.General.savePaymentInfo
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.ui.common.propertyChangedCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.math.RoundingMode

class CartViewModel(
    private val cartUseCase: ICartUseCase,
    private val productUseCase: IProductUseCase
): BaseViewModel(), ClickHandler<ACartListViewModel> {

    @Bindable
    var onRefreshScreen: Unit? = null

    @Bindable
    var askForBilling: Unit? = null

    @Bindable
    var onPopClicked: Unit? = null

    @Bindable
    var deleteItem: Int? = null

    @Bindable
    var subTotalAmount: Double = 0.0
        set(value){
            field = BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            notifyPropertyChanged(BR.subTotalAmount)
        }

    @Bindable
    var shippingAmount: Double = 0.0
        set(value){
            field = BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            notifyPropertyChanged(BR.shippingAmount)
        }

    @Bindable
    var shippingDescription: String = ""
        set(value){
            field = value
            notifyPropertyChanged(BR.shippingDescription)
        }

    @Bindable
    var totalAmount: Double = 0.0
        set(value){
            field = BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            notifyPropertyChanged(BR.totalAmount)
        }

    @Bindable
    var onBackClicked: Unit? = null

    var itemsComplete: List<Item> = listOf()

    @Bindable
    var items: List<ACartListViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    @Bindable
    var totalItems: String = "0"
        set(value){
            field = value
            notifyPropertyChanged(BR.totalItems)
        }

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
        checkIfComesFromPayment()

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
        var counterItems = 0

        itemsComplete = list
        context?.let{ c ->
            list.forEach {
                val viewModel = CartItemViewModel(context = c, item = it)
                viewModels.add(viewModel)

                subTotalAmount += (it.quantity!! * (it.price ?: 0.0))
                counterItems += it.quantity!!
            }
        }

        // List has at least one product (does not matter if there is services)
        val hasProduct: Item? = list.find { it.productType == 1 }
        val shoppingInformation = getStoreShoppingInfo() // get data in format: 1-1500-Fijo

        val shipping = shoppingInformation?.substringAfter("-")?.substringBefore("-")
        shippingAmount = if(hasProduct != null) shipping?.toDouble() ?: 0.0 else 0.0

        val description = shoppingInformation?.substringAfter("-")?.substringAfter("-")

        shippingDescription = if(description.isNullOrBlank() || description.equals("null", true)) ":" else "($description) :" // Fijo
        totalAmount = subTotalAmount + shippingAmount

        items = viewModels
        totalItems = counterItems.toString()
    }

    private fun checkIfComesFromPayment(){
        val response = getPaymentInfo()

        if(response.contains("true")){
            // it comes from payment screen
            progressVisible = true

            val storeId = response.substringAfter("-").substringBefore("-")
            val saleId = response.substringAfter("-").substringAfter("-")

            addDisposable(productUseCase.getPaymentStatus(storeId, saleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onStatusResponse, this::onError))
        }
    }

    private fun onStatusResponse(response: ResponsePaymentStatus){
        progressVisible = false
        error = "Estatus del pago: ${response.StatusPago1}"

        savePaymentInfo("false-0-0")

        if(response.StatusPago1 == 1){
            cartUseCase.deleteAllProductsOnCart()

            Handler().postDelayed({
                notifyPropertyChanged(BR.onRefreshScreen)
            }, 200)
        }
    }

    fun onDeleteItemClicked(itemId: Int){
        cartUseCase.deleteProductOnCart(itemId)

        Handler().postDelayed({
            notifyPropertyChanged(BR.onRefreshScreen)
        }, 200)
    }


    fun onPopClicked(@Suppress("UNUSED_PARAMETER") view: View){
        notifyPropertyChanged(BR.onPopClicked)
    }


    fun onBackClicked(@Suppress("UNUSED_PARAMETER") view: View?){
        notifyPropertyChanged(BR.onBackClicked)
    }


    fun onPayClicked(@Suppress("UNUSED_PARAMETER") v: View?){
        if(items.isNotEmpty()){
            val isUserLoggedIn = getUserSignedIn() && getUserId() > 0 && getUserName()!!.isNotBlank()

            if(isUserLoggedIn){
                // Before it proceeds to payment process, ask for billing data
                notifyPropertyChanged(BR.askForBilling)
            }else{
                // inform no user signed in
                error = "Inicie sesión para continuar con el pago de sus productos"
            }
        }
    }

    private fun onError(t: Throwable){
        progressVisible = false
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }


    override fun onClick(viewModel: ACartListViewModel) {
        /*if(viewModel is CartItemViewModel){

        }*/
    }
}