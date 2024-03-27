package com.ocean.domain.repositories

import com.ocean.domain.entities.ParcelData
import com.ocean.domain.entities.RegistrationData
import com.ocean.domain.entities.ResponsePaymentStatus
import io.reactivex.Single

interface IPaymentRepository {

    fun finalPaymentFirstStep(
        storeId: Int,
        remarks: String,
        clientId: Int,
        subTotalCost: Double,
        shippingCost: Double,
        totalCost: Double,
        requiresBilling: Int,
        rfc: String?,
        socialReason: String?,
        email: String?,
        parcel: ParcelData?
    ): Single<RegistrationData>

    /**
     * Make payment of each product on server
     */
    fun finalPaymentSecondStep(
        storeId: Int,
        saleId: Int,
        productId: Int,
        number: Int,
        quantity: Int,
        unitCost: Double,
        totalCost: Double,
        remarks: String
    ): Single<RegistrationData>

    fun consultPaymentStatus(storeId: String, saleId: String): Single<ResponsePaymentStatus>
}