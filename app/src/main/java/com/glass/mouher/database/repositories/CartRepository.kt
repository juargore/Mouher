package com.glass.mouher.database.repositories

import com.glass.domain.entities.Item
import com.glass.domain.repositories.ICartRepository
import com.glass.mouher.database.ItemDb
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.subjects.BehaviorSubject
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.kotlin.deleteFromRealm


class CartRepository(
    private val realm: Realm
): ICartRepository {

    private val itemObservable: BehaviorSubject<List<Item>> = BehaviorSubject.createDefault(emptyList())


    override fun getTotalProductsOnDb(): Observable<List<Item>> {
        return itemObservable
    }

    private fun getOrderPOD() : List<Item> {
        var pod = realm.where(ItemDb::class.java).findAll()

        if (pod == null) {
            return emptyList()
        }

        val mList = mutableListOf<Item>()

        pod.toList().forEach {
            mList.add(it.toItem())
        }

        return mList
    }

    override fun setProductOnCart(product: Item) {
        realm.executeTransaction { db ->
            val itemDb = getOrCreateItemInRealmThread(product.name ?: "")

            db.insertOrUpdate(itemDb)

            itemObservable.onNext(mutableListOf(itemDb.toItem()))
        }
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
    }
}