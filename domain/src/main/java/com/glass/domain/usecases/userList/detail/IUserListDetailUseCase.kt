package com.glass.domain.usecases.userList.detail

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IUserListDetailUseCase {

    fun startRegistration(listId: String)

    fun getListDetail(): Observable<List<Item>>

    fun stopRegistration()

}