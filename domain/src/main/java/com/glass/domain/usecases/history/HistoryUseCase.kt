package com.glass.domain.usecases.history

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IOrderRepository
import io.reactivex.Observable

class HistoryUseCase(
    private val ordersRepository: IOrderRepository
): IHistoryUseCase {

    init {
        ordersRepository.startOrderRegistration("julia.samperio@hotmail.com")
    }

    override fun getHistoryByUser(): Observable<List<Item>> {
        return ordersRepository.getAllOrdersByUser()
    }

    override fun addOrUpdateHistory() {}
}