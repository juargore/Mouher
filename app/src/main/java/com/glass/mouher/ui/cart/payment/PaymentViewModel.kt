package com.glass.mouher.ui.cart.payment

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.PaymentDataToSend
import com.glass.domain.entities.RegistrationData
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PaymentViewModel(
    private val productUseCase: IProductUseCase
): BaseViewModel() {

    var saleId = 0
    var hasErrors = true

    private lateinit var paymentData: PaymentDataToSend

    @Bindable
    var showDialog = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showDialog)
        }

    @Bindable
    var startLoadingWebPage: Unit? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.startLoadingWebPage)
        }

    @Bindable
    var error: String? = null
        set(value){
            field = value
            notifyPropertyChanged(BR.error)
        }


    fun initialize(data: PaymentDataToSend){
        paymentData = data
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)
    }

    fun startCreatingPayment(){
        showDialog = true

        with(paymentData) {
            val storeId = storeId
            val remarks = remarks
            val clientId = clientId
            val subTotalCost = subTotalCost
            val shippingCost = shippingCost
            val totalCost = totalCost
            val requiresBilling = requiresBilling
            val rfc = rfc
            val socialReason = socialReason
            val email = email
            val products = products

            addDisposable(productUseCase.makePaymentOfProducts(
                storeId, remarks, clientId, subTotalCost, shippingCost, totalCost, requiresBilling, rfc, socialReason, email, products
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    this@PaymentViewModel::onMakePaymentResponse,
                    this@PaymentViewModel::onError
                )
            )
        }
    }

    private fun onMakePaymentResponse(response: RegistrationData){
        if(response.Error > 0){
            hasErrors = true
            error = response.Mensaje
        }else{
            saleId = response.Id ?: 0
            hasErrors = false
            error = response.Mensaje
            notifyPropertyChanged(BR.startLoadingWebPage)
        }

        showDialog = false
    }

    private fun onError(t: Throwable){
        showDialog = false

        if(!t.message!!.contains("internet")){
            hasErrors = true
            error = t.message
        }
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}