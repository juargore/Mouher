package com.glass.data.repositories

import com.glass.data.serverapi.ProductApi
import com.glass.domain.entities.*
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

    override fun getSearchResults(word: String): Single<List<ProductSearchUI>> {
        val resultList = mutableListOf<ProductSearchUI>()

        return Single.create {
            val mList = listOf(
                ProductSearchUI(1, 1, "Taza ejemplo 1", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit", "https://proveedoradiez.com.mx/849-large_default/taza-para-cafe-embrocable-blanco-polar.jpg", "123.00", "205.50"),
                ProductSearchUI(1, 2, "Taza nueva 2", "Lorem ipsum dolor sit amet", "https://minisomx.vtexassets.com/arquivos/ids/169764/Taza-De-Cer-mica-We-Bare-Bears-Polar-1-4460.jpg?v=637395397175530000", "100.80", "190.00"),
                ProductSearchUI(1, 3, "Cama blanca 3", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit", "https://ss104.potterybarn.com.mx/xl/1074491827.jpg", "320.00", "350.50"),
                ProductSearchUI(1, 4, "Auto conocido 4", "Lorem ipsum dolor sit amet", "https://cdn.aarp.net/content/dam/aarp/auto/2017/07/1140-hyundai-ioniq-great-cars-road-trips-esp.imgcache.revfd63981c76a67e8a4ed28622bb76883e.web.900.518.jpg", "123.30", "205.60"),
                ProductSearchUI(1, 5, "Auto desconocido 5", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit", "https://siempreauto.com/wp-content/uploads/sites/9/2021/03/honda-accord-2021-01.jpg?quality=60&strip=all&w=1200", "800.00", "815.00")
            )

            mList.forEach { p -> if(p.name.contains(word, true)) resultList.add(p) }

            it.onSuccess(resultList + resultList)
        }
    }
}