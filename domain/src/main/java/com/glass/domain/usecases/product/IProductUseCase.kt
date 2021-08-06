package com.glass.domain.usecases.product

import com.glass.domain.entities.ProductUI
import com.glass.domain.entities.ReviewUI
import com.glass.domain.entities.ScreenTopInformationUI
import io.reactivex.Observable
import io.reactivex.Single

interface IProductUseCase {

    fun triggerToGetFullProduct(storeId: Int, productId: Int): Observable<Unit>

    fun getProductUI(): Observable<ProductUI>

    fun getTopScreenInformation(): Observable<ScreenTopInformationUI>

    fun getProductListByCategory(storeId: Int, categoryId: Int): Observable<List<ProductUI>>

    fun getRelatedProductsByProduct(): Observable<List<ProductUI>>

    fun getReviewsForProduct(): Single<List<ReviewUI>>
}