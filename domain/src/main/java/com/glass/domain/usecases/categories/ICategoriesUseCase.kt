package com.glass.domain.usecases.categories

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface ICategoriesUseCase {

    fun getAllCategories(): Observable<List<Item>>

}