package com.glass.domain.usecases.history.detail

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IOrderRepository
import io.reactivex.Observable

class HistoryDetailUseCase(
    private val ordersRepository: IOrderRepository
): IHistoryDetailUseCase{

    override fun startRegistration(listId: String) {
        ordersRepository.startOrderDetailRegistration(listId)
    }

    override fun getOrdertDetail(): Observable<List<Item>>{
        return ordersRepository.getAllProductsByOrder()
    }

    override fun stopRegistration() {
        ordersRepository.stopOrderDetailRegistration()
    }

}