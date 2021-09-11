package com.glass.domain.usecases.product

import com.glass.domain.entities.*
import io.reactivex.Observable
import io.reactivex.Single

interface IProductUseCase {

    fun triggerToGetFullProduct(storeId: Int, productId: Int): Observable<Unit>

    fun getProductUI(): Observable<ProductUI>

    fun getTopScreenInformation(fromDetail: Boolean = false): Observable<ScreenTopInformationUI>

    fun getProductListByCategory(storeId: Int, categoryId: Int): Observable<List<ProductUI>>

    fun getRelatedProductsByProduct(): Observable<List<ProductUI>>

    fun getReviewsForProduct(storeId: Int, productId: Int): Single<List<ReviewUI>>

    fun saveNewReviewForProduct(storeId: Int,
                                productId: Int,
                                userName: String,
                                userEmail: String,
                                userComment: String,
                                userRating: Float): Observable<ResponseUI>

    fun getHistoryByUser(userId: Int, startDate: String): Single<List<HistoryUI>>
}