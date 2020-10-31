package com.glass.domain.usecases.cart

import com.glass.domain.entities.Item
import com.glass.domain.repositories.ICartRepository
import io.reactivex.Observable

class CartUseCase(
    private val cartRepository: ICartRepository
): ICartUseCase {

    override fun getTotalProductsOnDb(): Observable<List<Item>> {
        return cartRepository.getTotalProductsOnDb()
    }

    override fun setProductOnCart(product: Item) {
        return cartRepository.setProductOnCart(product)
    }

    override fun deleteProductOnCart(idProduct: String) {
        return cartRepository.deleteProductOnCart(idProduct)
    }

}