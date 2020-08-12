package com.glass.data.repositories.firestore

import com.glass.domain.entities.Item
import com.glass.domain.repositories.ICategoriesRepository
import io.reactivex.Observable

class CategoriesRepository(

): ICategoriesRepository {

    override fun getAllCategories(): Observable<List<Item>> {
        return Observable.just(emptyList())
    }

    override fun addOrUpdateCategory() {

    }
}