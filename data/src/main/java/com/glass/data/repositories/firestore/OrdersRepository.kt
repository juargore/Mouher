package com.glass.data.repositories.firestore

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IOrderRepository
import io.reactivex.Observable

class OrdersRepository(

): IOrderRepository {
    override fun startOrderRegistration(userId: String) {

    }

    override fun startOrderDetailRegistration(listId: String) {

    }

    override fun getAllOrdersByUser(): Observable<List<Item>> {

        val mList = mutableListOf<Item>()
        mList.add(Item( name = "Carlos Esparza C.", description = "Av. Acueducto 1100 Col. Aviación"))
        mList.add(Item( name = "María Pérez Díaz", description = "Av. Acueducto 1100 Col. Aviación"))
        mList.add(Item( name = "Carlos Esparza C.", description = "Av. Acueducto 1100 Col. Aviación"))
        mList.add(Item( name = "Carlos Esparza C.", description = "Av. Acueducto 1100 Col. Aviación"))
        mList.add(Item( name = "María Pérez Díaz", description = "Av. Acueducto 1100 Col. Aviación"))
        mList.add(Item( name = "María Pérez Díaz", description = "Av. Acueducto 1100 Col. Aviación"))

        return Observable.just(mList)
    }

    override fun getAllProductsByOrder(): Observable<List<Item>> {
        return Observable.just(emptyList())
    }

    override fun addOrUpdateOrder() {

    }

    override fun stopOrderDetailRegistration() {

    }

}