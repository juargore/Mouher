package com.glass.domain.usecases.product

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IProductRepository
import io.reactivex.Observable

class ProductUseCase(
    private val productRepository: IProductRepository
): IProductUseCase {

    override fun startRegistration(productId: String) {
        productRepository.startRegistration(productId)
    }

    override fun getProductDetail(): Observable<Item> {
        return productRepository.getProductDetail()
    }

    override fun stopRegistration() {
        productRepository.stopRegistration()
    }

}