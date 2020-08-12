package com.glass.data.repositories.firestore

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IProductsRepository
import io.reactivex.Observable

class ProductsRepository (

): IProductsRepository{

    override fun startRegistration(categoryId: String) {

    }

    override fun addOrUpdateProduct() {

    }

    override fun getAllProducts(): Observable<List<Item>> {
        return Observable.just(emptyList())
    }

    override fun stopRegistration() {

    }
}