package com.glass.domain.usecases.products

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IProductsUseCase {

    fun startRegistration(categoryId: String)

    fun addOrUpdateProduct()

    fun getAllProducts(): Observable<List<Item>>

    fun stopRegistration()

}