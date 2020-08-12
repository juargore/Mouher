package com.glass.domain.usecases.productDetail

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IProductDetailUseCase {

    fun startRegistration(productId: String)

    fun getProductDetail(): Observable<Item>

    fun stopRegistration()

}