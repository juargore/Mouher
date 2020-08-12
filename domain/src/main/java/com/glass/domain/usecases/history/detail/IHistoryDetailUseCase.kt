package com.glass.domain.usecases.history.detail

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IHistoryDetailUseCase {

    fun startRegistration(listId: String)

    fun getOrdertDetail(): Observable<List<Item>>

    fun stopRegistration()
}