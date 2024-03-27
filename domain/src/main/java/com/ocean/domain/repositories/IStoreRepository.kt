package com.ocean.domain.repositories

import com.ocean.domain.entities.ResponseData
import com.ocean.domain.entities.StoreData
import io.reactivex.Observable

interface IStoreRepository {

    fun getAllStoreData(storeId: Int): Observable<StoreData>

    fun subscribeUserToNewsletter(userName: String, email: String, storeId: Int): Observable<ResponseData>
}