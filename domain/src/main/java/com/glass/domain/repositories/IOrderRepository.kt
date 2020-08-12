package com.glass.domain.repositories

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IOrderRepository {

    fun startOrderRegistration(userId: String)

    fun startOrderDetailRegistration(listId: String)

    fun getAllOrdersByUser(): Observable<List<Item>>

    fun getAllProductsByOrder(): Observable<List<Item>>

    fun addOrUpdateOrder()

    fun stopOrderDetailRegistration()
}