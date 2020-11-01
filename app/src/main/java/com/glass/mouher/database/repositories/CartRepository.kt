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

    override fun getSizeProductsOnDb(): Observable<String> = itemObservable.map { it.size.toString() }

    override fun setProductOnCart(product: Item) {
        realm.executeTransaction { db ->
            val itemDb = getOrCreateItemInRealmThread(product.name ?: "")
            db.insertOrUpdate(itemDb)

            updateBehaviorSubject()
        }
    }

    private fun getItemsOnDbAsList() : List<Item> {
        val pod = realm.where(ItemDb::class.java).findAll()
        val mList = mutableListOf<Item>()

        pod.toList().forEach {
            mList.add(it.toItem())
        }

        return mList
    }

    private fun updateBehaviorSubject(){
        itemObservable.onNext(getItemsOnDbAsList())
    }

    private fun getOrCreateItemInRealmThread(productUID: String) : ItemDb {
        var item = realm.where(ItemDb::class.java).equalTo("name", productUID).findFirst()

        if (item == null) {

            item = ItemDb()
            item.name = productUID

            item = realm.copyToRealm(item)

            return item
        }

        return item
    }

    override fun deleteProductOnCart(idProduct: String) {
        realm.beginTransaction()
        val pod = realm.where(ItemDb::class.java).equalTo("name", idProduct).findFirst()
        pod?.deleteFromRealm()
        realm.commitTransaction()

        updateBehaviorSubject()
    }

    override fun deleteAllProductsOnDb() {
        realm.beginTransaction()
        val pod = realm.where(ItemDb::class.java).findFirst()
        pod?.deleteFromRealm()
        realm.commitTransaction()

        updateBehaviorSubject()
    }
}