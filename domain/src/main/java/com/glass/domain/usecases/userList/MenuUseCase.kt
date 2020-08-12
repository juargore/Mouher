package com.glass.domain.usecases.userList

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IMenuRepository
import io.reactivex.Observable

class MenuUseCase(
    private val userListRepository: IMenuRepository
): IMenuUseCase {

    override fun getMenuItems(): Observable<List<Item>> = userListRepository.getMenuItems()
}