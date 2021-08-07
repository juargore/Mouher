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
    var icon: Int? = null
    var quantity: Int? = null
    var valueClassification: String? = null

    fun toItem() = Item(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        icon = icon,
        price = price,
        quantity= quantity,
        valueClassification = valueClassification
    )
}