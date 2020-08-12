package com.glass.domain.usecases.userList

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IMenuUseCase {

    fun getMenuItems(): Observable<List<Item>>
}