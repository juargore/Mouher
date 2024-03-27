package com.ocean.domain.usecases.cart

import com.ocean.domain.entities.Item
import io.reactivex.Observable

interface ICartUseCase {

    fun getTotalProductsOnDb(): Observable<List<Item>>

    fun getSizeProductsOnDb(): Observable<Int>

    fun setProductOnCart(product: Item)

    fun deleteProductOnCart(idProduct: Int)

    fun deleteAllProductsOnCart()
}