package com.glass.domain.repositories

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IProductDetailRepository {

    fun startRegistration(productId: String)

    fun getProductDetail(): Observable<Item>

    fun stopRegistration()

}