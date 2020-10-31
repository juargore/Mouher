package com.glass.mouher.database

import com.glass.domain.entities.Item
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class ItemDb: RealmModel {
    var name: String? = null
    var description: String? = null
    var imageUrl: String? = null
    var icon: Int? = null

    fun toItem() = Item(
        name = name,
        description = description,
        imageUrl = imageUrl,
        icon = icon
    )
}