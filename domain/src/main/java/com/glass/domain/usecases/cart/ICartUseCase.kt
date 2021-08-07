package com.glass.domain.usecases.cart

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface ICartUseCase {

    fun getTotalProductsOnDb(): Observable<List<Item>>

    fun getSizeProductsOnDb(): Observable<String>

    fun setProductOnCart(product: Item)

    fun deleteProductOnCart(idProduct: Int)

}