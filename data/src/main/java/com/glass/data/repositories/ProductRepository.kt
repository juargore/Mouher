package com.glass.data.repositories

import com.glass.data.serverapi.ProductApi
import com.glass.domain.common.or
import com.glass.domain.entities.FullProductDataResponse
import com.glass.domain.entities.NewArrivalProductData
import com.glass.domain.entities.ProductByCategoryData
import com.glass.domain.entities.ProductData
import com.glass.domain.repositories.IProductRepository
import io.reactivex.Observable

class ProductRepository(
    private val api: ProductApi
): IProductRepository {

    override fun triggerToGetFullProduct(
        storeId: Int,
        productId: Int
    ): Observable<FullProductDataResponse> {
        return api.getFullProduct(
            WebService = "ConsultaIntegralProducto",
            IdTienda = storeId.toString(),
            IdProducto = productId.toString()
        ).toObservable()
    }


    override fun getProductListByCategory(storeId: Int, categoryId: Int): Observable<ProductByCategoryData> {
        return api.getProductListByCategory(
            WebService = "ConsultaIntegralProductosPorCategoria",
            IdTienda = storeId.toString(),
            IdValorClasif1 = categoryId.toString()
        ).toObservable()
    }

}