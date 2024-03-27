package com.ocean.domain.usecases.product

import com.ocean.domain.entities.*
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

    fun makePaymentOfProducts(
        storeId: Int,
        remarks: String,
        clientId: Int,
        subTotalCost: Double,
        shippingCost: Double,
        totalCost: Double,
        requiresBilling: Int,
        rfc: String?,
        socialReason: String?,
        email: String?,
        products: List<Item>,
        parcel : ParcelData?
    ): Single<RegistrationData>

    fun getPaymentStatus(storeId: String, saleId: String): Single<ResponsePaymentStatus>

    fun getSearchResults(word: String): Single<List<ProductSearchUI>>

    fun getParcelsPrices(storeId: Int, clientId: Int, productsIds: List<Int>, quantities: List<Int>) : Single<ParcelsResponse>
}