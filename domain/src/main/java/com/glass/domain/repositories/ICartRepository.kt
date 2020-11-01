package com.glass.domain.repositories

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface ICartRepository {

    fun getTotalProductsOnDb(): Observable<List<Item>>

    fun getSizeProductsOnDb(): Observable<String>

    fun setProductOnCart(product: Item)

    fun deleteProductOnCart(idProduct: String)

    fun deleteAllProductsOnDb()

}