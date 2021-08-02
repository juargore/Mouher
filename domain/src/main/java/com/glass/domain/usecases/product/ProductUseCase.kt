package com.glass.domain.usecases.product

import com.glass.domain.entities.ProductUI
import com.glass.domain.entities.ReviewUI
import com.glass.domain.entities.ShortProductUI
import com.glass.domain.repositories.IProductRepository
import io.reactivex.Observable
import io.reactivex.Single

class ProductUseCase(
    private val productRepository: IProductRepository
): IProductUseCase {

    override fun getNewArrivalsForStore(storeId: String): Observable<List<ShortProductUI>> {
        return productRepository.getNewArrivalsForStoreData(storeId).map { listNewArrivalProductData ->

            val mList = mutableListOf<ShortProductUI>()

            listNewArrivalProductData.forEach {
                mList.add(getFullProduct(it.IdProducto ?: "", storeId))
            }
            return@map mList
        }
    }

    override fun getProductUI(productId: String, storeId: String): Observable<ProductUI> {
        return Observable.just(ProductUI())
    }

    override fun getProductListByCategory(storeId: Int, categoryId: Int): Observable<List<ProductUI>> {
        val mList = mutableListOf<ProductUI>()

        return productRepository.getProductListByCategory(storeId, categoryId)
            .map { data->
                data.Productos?.forEach {
                    mList.add(it.toProductUI())
                }
                return@map mList
            }
    }

    override fun getReviewsForProduct(productId: String): Single<List<ReviewUI>> {
        val mList = mutableListOf<ReviewUI>()
        mList.add(ReviewUI(id = "0", userName = "Carlos Perez", date = "23 Sep 2020", rating = "", review = "Bueno y barato", urlPhoto = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/User_icon_2.svg/220px-User_icon_2.svg.png"))
        mList.add(ReviewUI(id = "1", userName = "Juan Díaz", date = "12 Oct 2020", rating = "", review = "Me ha gustado mucho", urlPhoto = "https://static.thenounproject.com/png/17241-200.png"))

        return Single.just(mList)
    }

    private fun getFullProduct(id: String, storeId: String): ShortProductUI{
        return ShortProductUI()
    }

}