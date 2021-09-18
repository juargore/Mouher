package com.glass.mouher.database.repositories

import com.glass.domain.entities.Item
import com.glass.domain.repositories.ICartRepository
import com.glass.mouher.database.ItemDb
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.realm.Realm
import io.realm.kotlin.deleteFromRealm


class CartRepository(
    private val realm: Realm
): ICartRepository {

    private var itemObservable: BehaviorSubject<List<Item>> = BehaviorSubject.createDefault(emptyList())

    init {
        itemObservable = BehaviorSubject.createDefault(emptyList())
        updateBehaviorSubject()
    }

    override fun getTotalProductsOnDb(): Observable<List<Item>> = itemObservable

    override fun getSizeProductsOnDb(): Observable<Int> = itemObservable.map { it.size }

    override fun setProductOnCart(product: Item) {
        realm.executeTransaction { db ->
            val itemDb = getOrCreateItemInRealmThread(product)
            db.insertOrUpdate(itemDb)

            updateBehaviorSubject()
        }
    }

    private fun getItemsOnDbAsList() : List<Item> {
        val allItems = realm.where(ItemDb::class.java).findAll()
        val mList = mutableListOf<Item>()

        allItems.toList().forEach {
            mList.add(it.toItem())
        }

        return mList
    }

    private fun updateBehaviorSubject(){
        itemObservable.onNext(getItemsOnDbAsList())
    }

    private fun getOrCreateItemInRealmThread(product: Item) : ItemDb {
        var item = realm.where(ItemDb::class.java).equalTo("id", product.id).findFirst()

        if (item == null) {

            item = ItemDb()
            item.id = product.id
            item.name = product.name
            item.description = product.description
            item.price = product.price
            item.imageUrl = product.imageUrl
            item.quantity = product.quantity
            item.valueClassification = product.valueClassification
            item.storeId = product.storeId
            item.productType = product.productType

            item = realm.copyToRealm(item)

            return item
        }

        return item
    }

    override fun deleteProductOnCart(idProduct: Int) {
        realm.beginTransaction()
        val itemDb = realm.where(ItemDb::class.java).equalTo("id", idProduct).findFirst()
        itemDb?.deleteFromRealm()
        realm.commitTransaction()

        updateBehaviorSubject()
    }

    override fun deleteAllProductsOnDb() {
        realm.beginTransaction()
        val allItems = realm.where(ItemDb::class.java).findAll()
        allItems?.deleteAllFromRealm()
        realm.commitTransaction()

        updateBehaviorSubject()
    }
}