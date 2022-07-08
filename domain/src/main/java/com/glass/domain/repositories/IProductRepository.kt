package com.glass.domain.repositories

import com.glass.domain.entities.*
import io.reactivex.Observable
import io.reactivex.Single

interface IProductRepository {

    fun triggerToGetFullProduct(storeId: Int, productId: Int): Observable<FullProductDataResponse>

    fun getProductListByCategory(storeId: Int, categoryId: Int): Observable<ProductByCategoryData>

    fun saveNewReviewForProduct(storeId: Int,
                                productId: Int,
                                userName: String,
                                userEmail: String,
                                userComment: String,
                                userRating: Float): Observable<ResponseData>

    fun getHistoryForUser(userId: Int, startDate: String): Single<HistoryResponse>

    fun getSearchResults(word: String): Single<ProductSearchResponse>

    fun getParcelsPrices(storeId: Int, clientId: Int, productsIds: List<Int>, quantities: List<Int>) : Single<ParcelsResponse>
}