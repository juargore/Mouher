package com.glass.domain.usecases.history

import com.glass.domain.entities.Item
import io.reactivex.Observable

interface IHistoryUseCase {

    fun getHistoryByUser(): Observable<List<Item>>

    fun addOrUpdateHistory()
}