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

    override fun getReviewsForProduct(storeId: Int, productId: Int): Single<List<ReviewUI>> {
        return productRepository.triggerToGetFullProduct(storeId, productId)
            .map { response->
                val mList = mutableListOf<ReviewUI>()

                response.Reseñas?.forEach {
                    mList.add(it.toReviewUI())
                }
                return@map mList.toList()
        }.firstOrError()
    }

    override fun saveNewReviewForProduct(
        storeId: Int,
        productId: Int,
        userName: String,
        userEmail: String,
        userComment: String,
        userRating: Float
    ): Observable<ResponseUI> {
        return productRepository.saveNewReviewForProduct(storeId, productId, userName, userEmail, userComment, userRating)
            .map { it.toResponseUI() }
    }

    override fun getRelatedProductsByProduct(): Observable<List<ProductUI>> {
        val mList = mutableListOf<ProductUI>()

        fullProductDataResponse?.ProductosOfrecidos?.forEach {
            mList.add(it.toProductUI())
        }

        return Observable.just(mList)
    }

}