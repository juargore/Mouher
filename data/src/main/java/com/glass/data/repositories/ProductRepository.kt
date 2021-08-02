package com.glass.data.repositories

import com.glass.data.serverapi.ProductApi
import com.glass.domain.common.or
import com.glass.domain.entities.NewArrivalProductData
import com.glass.domain.entities.ProductByCategoryData
import com.glass.domain.entities.ProductData
import com.glass.domain.repositories.IProductRepository
import io.reactivex.Observable

class ProductRepository(
    private val api: ProductApi
): IProductRepository {

    override fun getNewArrivalsForStoreData(storeId: String): Observable<List<NewArrivalProductData>> {

        return api.getNewArrivalsForStore(
            WebService = "ConsultaRegProductoNuevoTodos",
            IdBDD = storeId
        ).map { response ->
            response.Datos?.let { listData ->
                listData
            }.or {
                emptyList()
            }
        }.toObservable()
    }

    override fun getFullProductData(productId: String, storeId: String): Observable<ProductData> {

        return api.getFullProductData(
            WebService = "ConsultaCatProductoId",
            IdBDD = storeId,
            Id = productId
        ).map { response ->
            response.Datos?.let {  listData ->
                listData[0]
            }.or {
                ProductData()
            }
        }.toObservable()
    }

    override fun getProductListByCategory(storeId: Int, categoryId: Int): Observable<ProductByCategoryData> {
        return api.getProductListByCategory(
            WebService = "ConsultaIntegralProductosPorCategoria",
            IdTienda = storeId.toString(),
            IdValorClasif1 = categoryId.toString()
        ).toObservable()
    }

}