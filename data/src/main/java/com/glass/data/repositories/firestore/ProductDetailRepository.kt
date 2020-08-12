package com.glass.data.repositories.firestore

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IProductDetailRepository
import io.reactivex.Observable

class ProductDetailRepository(

): IProductDetailRepository {
    override fun startRegistration(productId: String) {

    }

    override fun getProductDetail(): Observable<Item> {
        return Observable.just(Item())
    }

    override fun stopRegistration() {

    }

}