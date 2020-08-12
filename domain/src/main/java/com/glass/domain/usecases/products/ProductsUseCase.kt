package com.glass.domain.usecases.products

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IProductsRepository
import io.reactivex.Observable

class ProductsUseCase(
    private val productsRepository: IProductsRepository
): IProductsUseCase {


    override fun startRegistration(categoryId: String) {
        productsRepository.startRegistration(categoryId)
    }

    override fun addOrUpdateProduct() {}

    override fun getAllProducts(): Observable<List<Item>> {
        return productsRepository.getAllProducts()
    }

    override fun stopRegistration() {
        productsRepository.stopRegistration()
    }

}