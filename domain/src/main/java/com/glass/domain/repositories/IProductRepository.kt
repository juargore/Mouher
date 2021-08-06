package com.glass.domain.repositories

import com.glass.domain.entities.FullProductDataResponse
import com.glass.domain.entities.NewArrivalProductData
import com.glass.domain.entities.ProductByCategoryData
import com.glass.domain.entities.ProductData
import io.reactivex.Observable

interface IProductRepository {

    fun triggerToGetFullProduct(storeId: Int, productId: Int): Observable<FullProductDataResponse>

    fun getProductListByCategory(storeId: Int, categoryId: Int): Observable<ProductByCategoryData>
}