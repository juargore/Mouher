package com.ocean.domain.repositories

import com.ocean.domain.entities.Item
import io.reactivex.Observable

interface ICartRepository {

    fun getTotalProductsOnDb(): Observable<List<Item>>

    fun getSizeProductsOnDb(): Observable<Int>

    fun setProductOnCart(product: Item)

    fun deleteProductOnCart(idProduct: Int)

    fun deleteAllProductsOnDb()
}