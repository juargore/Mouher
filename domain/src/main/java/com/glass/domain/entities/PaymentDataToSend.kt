package com.glass.domain.entities

import java.io.Serializable

data class PaymentDataToSend(
    val storeId: Int,
    val remarks: String,
    val clientId: Int,
    val subTotalCost: Double,
    val shippingCost: Double,
    val totalCost: Double,
    var requiresBilling: Int,
    var rfc: String?,
    var socialReason: String?,
    var email: String?,
    val products: List<Item>
): Serializable