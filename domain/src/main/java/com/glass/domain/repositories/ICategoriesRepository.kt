package com.glass.domain.repositories

import com.glass.domain.entities.Item
import io.reactivex.Observable


interface ICategoriesRepository {

    fun getAllCategories(): Observable<List<Item>>

    fun addOrUpdateCategory()
}