package com.glass.mouher.database

import com.glass.domain.entities.Item
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class ItemDb: RealmModel {
    var id: Int? = null
    var name: String? = null
    var description: String? = null
    var imageUrl: String? = null
    var price: Double? = null
    var quantity: Int? = null
    var valueClassification: String? = null
    var storeId: Int? = null

    fun toItem() = Item(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        price = price,
        quantity = quantity,
        valueClassification = valueClassification,
        storeId = storeId
    )
}