package com.glass.data.repositories

import com.glass.data.serverapi.ProductApi
import com.glass.domain.entities.FullProductDataResponse
import com.glass.domain.entities.HistoryResponse
import com.glass.domain.entities.ProductByCategoryData
import com.glass.domain.entities.ResponseData
import com.glass.domain.repositories.IProductRepository
import io.reactivex.Observable
import io.reactivex.Single

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

    override fun saveNewReviewForProduct(
        storeId: Int,
        productId: Int,
        userName: String,
        userEmail: String,
        userComment: String,
        userRating: Float
    ): Observable<ResponseData> {

        return api.saveNewReviewForProduct(
            WebService = "GuardaReseña",
            IdTienda = storeId.toString(),
            IdProducto = productId.toString(),
            Nombre = userName,
            Correo = userEmail,
            Reseña = userComment,
            Valoracion = userRating.toString()
        ).toObservable()
    }

    override fun getHistoryForUser(userId: Int, startDate: String): Single<HistoryResponse> {
        return api.getHistoryProducts(
            WebService = "ConsultaIntegralVentasCliente",
            IdCliente = userId,
            FechaInicial = startDate,
            FechaFinal = "#FM"
        )
    }

}