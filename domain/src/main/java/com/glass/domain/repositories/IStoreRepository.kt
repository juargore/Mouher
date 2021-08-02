package com.glass.domain.repositories

import com.glass.domain.entities.ResponseData
import com.glass.domain.entities.ResponseUI
import com.glass.domain.entities.StoreData
import io.reactivex.Observable

interface IStoreRepository {

    fun getAllStoreData(storeId: Int): Observable<StoreData>

    fun subscribeUserToNewsletter(userName: String, email: String, storeId: Int): Observable<ResponseData>
}