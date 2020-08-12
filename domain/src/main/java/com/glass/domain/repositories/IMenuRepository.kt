package com.glass.domain.repositories

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IMenuRepository {

    fun getMenuItems(): Observable<List<Item>>
}