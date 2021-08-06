package com.glass.domain.usecases.product

import com.glass.domain.entities.*
import com.glass.domain.repositories.IProductRepository
import io.reactivex.Observable
import io.reactivex.Single

class ProductUseCase(
    private val productRepository: IProductRepository
): IProductUseCase {

    private var productByCategoryData: ProductByCategoryData? = null
    private var fullProductDataResponse: FullProductDataResponse? = null


    override fun triggerToGetFullProduct(storeId: Int, productId: Int): Observable<Unit> {
        return productRepository.triggerToGetFullProduct(storeId, productId).map {
            fullProductDataResponse = it
        }
    }

    override fun getProductUI(): Observable<ProductUI> {
        return Observable.just(fullProductDataResponse?.getProductUI())
    }

    override fun getProductListByCategory(storeId: Int, categoryId: Int): Observable<List<ProductUI>> {
        val mList = mutableListOf<ProductUI>()

        return productRepository.getProductListByCategory(storeId, categoryId)
            .map { data->
                productByCategoryData = data

                data.Productos?.forEach {
                    mList.add(it.toProductUI())
                }
                return@map mList
            }
    }

    override fun getTopScreenInformation(): Observable<ScreenTopInformationUI> {
        return Observable.just(productByCategoryData?.getScreenTopInfo())
    }

    override fun getReviewsForProduct(): Single<List<ReviewUI>> {
        val mList = mutableListOf<ReviewUI>()

        fullProductDataResponse?.Reseñas?.forEach {
            mList.add(it.toReviewUI())
        }
        return Single.just(mList)
    }

    override fun getRelatedProductsByProduct(): Observable<List<ProductUI>> {
        val mList = mutableListOf<ProductUI>()

        fullProductDataResponse?.ProductosOfrecidos?.forEach {
            mList.add(it.toProductUI())
        }

        return Observable.just(mList)
    }

}