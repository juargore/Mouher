package com.glass.domain.usecases.product

import com.glass.domain.entities.ProductUI
import com.glass.domain.entities.ShortProductUI
import com.glass.domain.repositories.IProductRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ProductUseCase(
    private val productRepository: IProductRepository
): IProductUseCase {

    override fun getNewArrivalsForStore(storeId: String): Observable<List<ShortProductUI>> {
        return productRepository.getNewArrivalsForStoreData(storeId).map { listNewArrivalProductData ->

            val mList = mutableListOf<ShortProductUI>()

            listNewArrivalProductData.forEach {
                mList.add(getFullProduct(it.IdProducto ?: ""))
            }
            return@map mList
        }
    }

    override fun getProductUI(productId: String): Observable<ProductUI> {
        return productRepository.getFullProductData(productId).map {
            return@map it.getProductUI()
        }
    }

    private fun getFullProduct(id: String): ShortProductUI{
        return productRepository.getFullProductData(id)
            .subscribeOn(Schedulers.io())
            .map { productData ->
                return@map productData.getShortProductUI()
            }
            .blockingFirst()
    }

}