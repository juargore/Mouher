package com.ocean.domain.usecases.cart

import com.ocean.domain.entities.Item
import com.ocean.domain.repositories.ICartRepository
import io.reactivex.Observable

class CartUseCase(
    private val cartRepository: ICartRepository
): ICartUseCase {

    override fun getTotalProductsOnDb(): Observable<List<Item>> = cartRepository.getTotalProductsOnDb()

    override fun getSizeProductsOnDb(): Observable<Int> = cartRepository.getSizeProductsOnDb()

    override fun setProductOnCart(product: Item) {
        cartRepository.setProductOnCart(product)
    }

    override fun deleteProductOnCart(idProduct: Int) {
        cartRepository.deleteProductOnCart(idProduct)
    }

    override fun deleteAllProductsOnCart() {
        cartRepository.deleteAllProductsOnDb()
    }
}