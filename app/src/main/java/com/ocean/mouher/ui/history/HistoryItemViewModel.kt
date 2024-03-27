package com.ocean.mouher.ui.history

import android.content.Context
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.ocean.domain.entities.HistoryUI

class HistoryItemViewModel(
    val context: Context,
    val history: HistoryUI
): AHistoryListViewModel() {

    val id = history.id

    val storeName = history.storeName

    val date = "Fecha: ${history.date}"

    val folio = "Folio: ${history.folio}"

    val subTotalAmount = "$ ${history.subTotalAmount}"

    val shippingFee = "$ ${history.shippingFee}"

    val totalAmount = "$ ${history.totalAmount}"

    val paymentStatus = history.paymentStatus

    val colorPaymentStatus = history.colorPaymentStatus

    val shipmentStatus = history.shipmentStatus

    val colorShipmentStatus = history.colorShipmentStatus

    val urlForDetails = history.urlForDetails

    fun onDetailsClicked(@Suppress("UNUSED_PARAMETER") v: View){
        notifyPropertyChanged(BR.onDetailsClicked)
    }

}