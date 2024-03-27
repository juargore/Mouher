package com.ocean.mouher.ui.common

/**
 * Interface that items (ViewModels) should implement to use with Diffutils.
 */
interface DiffItem<T> {
    /**
     * Uid of the item.
     */
    val uid: String

    /**
     * Does [this] and an [other] item stands for the same thing.
     */
    fun isTheSame(other: T): Boolean

    /**
     * Does [this] and an [other] item have the same content.
     */
    fun isSameContent(other: T): Boolean
}
