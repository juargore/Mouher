package com.glass.domain.repositories

import com.glass.domain.entities.NewArrivalProductData
import com.glass.domain.entities.ProductByCategoryData
import com.glass.domain.entities.ProductData
import io.reactivex.Observable

interface IProductRepository {

    fun getNewArrivalsForStoreData(storeId: String): Observable<List<NewArrivalProductData>>

    fun getFullProductData(productId: String, storeId: String): Observable<ProductData>

    fun getProductListByCategory(storeId: Int, categoryId: Int): Observable<ProductByCategoryData>
}