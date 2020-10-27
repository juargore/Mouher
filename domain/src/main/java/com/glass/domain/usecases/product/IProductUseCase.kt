package com.glass.domain.usecases.product

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IProductUseCase {

    fun startRegistration(productId: String)

    fun getProductDetail(): Observable<Item>

    fun stopRegistration()

}