package com.glass.domain.usecases.userList.detail

import com.glass.domain.entities.Item
import com.glass.domain.repositories.IMenuRepository
import io.reactivex.Observable

class UserListDetailUseCase(
    private val userListRepository: IMenuRepository
): IUserListDetailUseCase {

    override fun startRegistration(listId: String) {

    }

    override fun getListDetail(): Observable<List<Item>> = Observable.just(emptyList())

    override fun stopRegistration() {

    }
}