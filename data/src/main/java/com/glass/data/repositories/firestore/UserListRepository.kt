package com.glass.data.repositories.firestore

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IMenuRepository
import io.reactivex.Observable

class UserListRepository(

): IMenuRepository {

    override fun getMenuItems(): Observable<List<Item>> {
        return Observable.just(emptyList())
    }

    override fun getMenuSocialMediaItems(): Observable<List<Item>> {
        return Observable.just(emptyList())
    }
}