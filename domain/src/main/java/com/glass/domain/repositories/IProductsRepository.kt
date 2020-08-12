package com.glass.domain.repositories

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IProductsRepository {

    fun startRegistration(categoryId: String)

    fun addOrUpdateProduct()

    fun getAllProducts(): Observable<List<Item>>

    fun stopRegistration()

}