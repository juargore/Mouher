package com.glass.domain.usecases.productDetail

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IProductDetailRepository
import io.reactivex.Observable

class ProductDetailUseCase(
    private val productDetailRepository: IProductDetailRepository
): IProductDetailUseCase {

    override fun startRegistration(productId: String) {
        productDetailRepository.startRegistration(productId)
    }

    override fun getProductDetail(): Observable<Item> {
        return productDetailRepository.getProductDetail()
    }

    override fun stopRegistration() {
        productDetailRepository.stopRegistration()
    }

}