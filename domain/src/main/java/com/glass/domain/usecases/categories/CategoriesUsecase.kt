package com.glass.domain.usecases.categories

import com.glass.domain.entities.Item
import com.glass.domain.repositories.ICategoriesRepository
import io.reactivex.Observable

class CategoriesUsecase(
    private val categoriesRepository: ICategoriesRepository
): ICategoriesUseCase {


    override fun getAllCategories(): Observable<List<Item>> {
        return categoriesRepository.getAllCategories()
    }
}