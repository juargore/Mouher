package com.glass.data.repositories

import com.glass.data.serverapi.ProductApi
import com.glass.domain.common.or
import com.glass.domain.entities.NewArrivalProductData
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

    override fun getFullProductData(productId: String): Observable<ProductData> {

        return api.getFullProductData(
            WebService = "ConsultaCatProductoId",
            IdBDD = "1",
            Id = productId
        ).map { response ->
            response.Datos?.let {  listData ->
                listData[0]
            }.or {
                ProductData()
            }
        }.toObservable()
    }

}