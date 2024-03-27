@file:Suppress("UNUSED_PARAMETER", "DEPRECATION", "UNUSED_PARAMETER")
@file:SuppressLint("StaticFieldLeak")
package com.ocean.mouher.ui.cart

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.ocean.domain.entities.*
import com.ocean.domain.usecases.cart.ICartUseCase
import com.ocean.domain.usecases.product.IProductUseCase
import com.ocean.domain.usecases.user.IUserUseCase
import com.ocean.mouher.R
import com.ocean.mouher.shared.General.getPaymentInfo
import com.ocean.mouher.shared.General.getStoreShoppingInfo
import com.ocean.mouher.shared.General.getUserId
import com.ocean.mouher.shared.General.getUserName
import com.ocean.mouher.shared.General.getUserSignedIn
import com.ocean.mouher.shared.General.savePaymentInfo
import com.ocean.mouher.ui.base.BaseViewModel
import com.ocean.mouher.ui.common.SnackType
import com.ocean.mouher.ui.common.binder.ClickHandler
import com.ocean.mouher.ui.common.propertyChangedCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.math.RoundingMode

class CartViewModel(
    private val context: Context,
    private val cartUseCase: ICartUseCase,
    private val productUseCase: IProductUseCase,
    private val userUseCase: IUserUseCase
): BaseViewModel(), ClickHandler<ACartListViewModel> {

    var snackType: SnackType = SnackType.INFO

    @Bindable
    var shippingLabelColor = context.resources.getColor(R.color.onyxBlack)
        set(value) {
            field = value
            notifyPropertyChanged(BR.shippingLabelColor)
        }

    @Bindable
    var onFinishScreen: Unit? = null

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
        set(value) {
            field = BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            notifyPropertyChanged(BR.subTotalAmount)
        }

    @Bindable
    var parcelSelected : ParcelData? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.parcelSelected)
            updateShippingAmount()
        }

    @Bindable
    var hasActiveAddress : Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasActiveAddress)
        }

    private fun updateShippingAmount() {
        shippingAmount = parcelSelected?.Importe ?: 0.0
    }

    @Bindable
    var shippingAmount = 0.0
        set(value) {
            field = BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            notifyPropertyChanged(BR.shippingAmount)
            updateTotalAmount()
        }

    private fun updateTotalAmount() {
        totalAmount = subTotalAmount + shippingAmount
        parcelSelected?.Descripcion?.let {
            shippingDescription = "($it)"
        }
    }

    @Bindable
    var totalAmount = 0.0
        set(value) {
            field = BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            notifyPropertyChanged(BR.totalAmount)
        }

    @Bindable
    var shippingDescription: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.shippingDescription)
        }


    @Bindable
    var onBackClicked: Unit? = null

    var itemsComplete: List<Item> = listOf()

    @Bindable
    var items: List<ACartListViewModel> = listOf()
        set(value) {
            field = value
            notifyPropertyChanged(BR.items)
        }

    @Bindable
    var totalItems: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalItems)
        }

    @Bindable
    var error: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.error)
        }

    @Bindable
    var parcelsResponse: ParcelsResponse? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.parcelsResponse)
        }

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }

    @Bindable
    var progressParcelsVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressParcelsVisible)
        }

    val itemPropertyChangedCallback =
            propertyChangedCallback { sender: Observable?, propertyId: Int ->
                if(sender is CartItemViewModel) {
                    when(propertyId) {
                        BR.deleteClicked -> {
                            deleteItem = sender.id
                            notifyPropertyChanged(BR.deleteItem)
                        }
                    }
                }
            }

    private var firstTime = true
    private var firstTimeParcel = true

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    fun getDataAfterLoginAndAddressValidations() {
        checkIfComesFromPayment()

        if (firstTime) {
            firstTime = false
            addDisposable(cartUseCase.getTotalProductsOnDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe { onTotalProductsDbResponse(it) })
        }
    }

    private fun getParcelPrices() {
        if (firstTimeParcel) {
            firstTimeParcel = false
            progressParcelsVisible = true

            val productsIds = mutableListOf<Int>()
            val quantities = mutableListOf<Int>()
            val storeId = itemsComplete[0].storeId ?: 0
            val clientId = getUserId()

            itemsComplete.forEach { item ->
                item.id?.let { productsIds.add(it) }
                item.quantity?.let { quantities.add(it) }
            }

            addDisposable(productUseCase.getParcelsPrices(storeId, clientId, productsIds, quantities)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::onParcelsPriceResponse, this::onError)
            )
        }
    }

    private fun onParcelsPriceResponse(data: ParcelsResponse) {
        shippingLabelColor = context.resources.getColor(R.color.mainDarkBlue)
        progressParcelsVisible = false

        data.Opciones?.forEach {
            it.Alto = data.Alto
            it.Ancho = data.Ancho
            it.Largo = data.Largo
            it.Peso = data.Peso
            it.Fragilidad = data.Fragilidad
        }
        parcelsResponse = data
    }

    fun getUserAddressToValidate() {
        addDisposable(userUseCase.getUserData(getUserId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onUserDataResponse, this::onError)
        )
    }

    private fun onUserDataResponse(response: UserProfileData) {
        hasActiveAddress = response.DomicilioEnvio != null
    }

    private fun onTotalProductsDbResponse(list: List<Item>) {
        val viewModels = mutableListOf<ACartListViewModel>()
        var counterItems = 0

        itemsComplete = list
        context.let { c ->
            list.forEach {
                val viewModel = CartItemViewModel(context = c, item = it)
                viewModels.add(viewModel)

                subTotalAmount += (it.quantity!! * (it.price ?: 0.0))
                counterItems += it.quantity!!
            }
        }

        // List has at least one product (doesn't matter if the rest are services)
        val hasProduct: Item? = list.find { it.productType == 1 }
        val shoppingInformation = getStoreShoppingInfo() // get data in format: 1-1500-Fijo

        val shippingType = shoppingInformation?.substringBefore("-") // 1 or 2 or 3
        val shippingCost = shoppingInformation?.substringAfter("-")?.substringBefore("-") // 1500
        shippingAmount = if (hasProduct != null) shippingCost?.toDouble() ?: 0.0 else 0.0

        val description = shoppingInformation?.substringAfterLast("-") // Fijo or Dinamico or Variable(not used yet)
        println("Tipo de envio: $shippingType - $description")

        shippingDescription = if (description.isNullOrBlank() || description.equals("null", true)) ":" else "($description) :" // Fijo
        if (hasProduct == null) shippingDescription = "(No aplica) :"
        if (list.isEmpty()) shippingDescription = "(Pendiente) :"

        items = viewModels
        totalItems = counterItems.toString()

        // only if user has at least one product, proceed to check shipping parcel
        if (hasProduct != null && shippingType == "3") { // is dynamic price
            if (counterItems > 0) {
                shippingDescription = ":"
                shippingAmount = 0.0
                getParcelPrices()
            }
        }
    }

    private fun checkIfComesFromPayment() {
        val response = getPaymentInfo()
        if (response.contains("true")) { // it comes from payment screen
            progressVisible = true
            val saleId = response.substringAfterLast("-")
            val storeId = response.substringAfter("-").substringBefore("-")
            addDisposable(productUseCase.getPaymentStatus(storeId, saleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onStatusResponse, this::onError)
            )
        }
    }

    private fun onStatusResponse(response: ResponsePaymentStatus) {
        progressVisible = false
        savePaymentInfo("false-0-0")

        if (response.StatusPago1 == 1) {
            cartUseCase.deleteAllProductsOnCart()
            Handler().postDelayed({
                notifyPropertyChanged(BR.onFinishScreen)
            }, 200)
        } else {
            snackType = SnackType.ERROR
            error = "Su pago no fue procesado. Esta compra quedará registrada como pendiente de pago."
        }
    }

    fun onDeleteItemClicked(itemId: Int) {
        cartUseCase.deleteProductOnCart(itemId)
        Handler().postDelayed({
            notifyPropertyChanged(BR.onRefreshScreen)
        }, 200)
    }

    fun refreshScreen(v: View?) {
        if (shippingLabelColor == context.resources.getColor(R.color.mainDarkBlue)) {
            notifyPropertyChanged(BR.onRefreshScreen)
        }
    }

    fun onPopClicked(view: View?) {
        notifyPropertyChanged(BR.onPopClicked)
    }

    fun onBackClicked(view: View?) {
        notifyPropertyChanged(BR.onBackClicked)
    }

    fun onPayClicked(v: View?) {
        if (items.isNotEmpty()) {
            val isUserLoggedIn = getUserSignedIn() && getUserId() > 0 && getUserName()!!.isNotBlank()
            if (isUserLoggedIn) {
                // Before it proceeds to payment process, ask for billing data
                notifyPropertyChanged(BR.askForBilling)
            } else {
                // inform no user signed in
                snackType = SnackType.WARNING
                error = "Inicie sesión para continuar con el pago."
            }
        }
    }

    private fun onError(t: Throwable) {
        progressParcelsVisible = false
        progressVisible = false
        println("ERROR: ${t.localizedMessage}")
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }


    override fun onClick(viewModel: ACartListViewModel) {
        /*if(viewModel is CartItemViewModel) {}*/
    }
}