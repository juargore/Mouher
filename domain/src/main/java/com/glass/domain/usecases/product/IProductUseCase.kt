package com.glass.domain.usecases.product

import com.glass.domain.entities.ProductUI
import com.glass.domain.entities.ReviewUI
import com.glass.domain.entities.ShortProductUI
import io.reactivex.Observable
import io.reactivex.Single

interface IProductUseCase {

    fun getNewArrivalsForStore(storeId: String): Observable<List<ShortProductUI>>

    fun getProductUI(productId: String, storeId: String): Observable<ProductUI>

    fun getProductListByCategory(categoryId: String): Observable<List<ProductUI>>

    fun getReviewsForProduct(productId: String): Single<List<ReviewUI>>
}