package com.glass.data.repositories

import com.glass.data.serverapi.StoreApi
import com.glass.domain.entities.ResponseData
import com.glass.domain.entities.StoreData
import com.glass.domain.repositories.IStoreRepository
import io.reactivex.Observable

class StoreRepository(
    private val api: StoreApi
): IStoreRepository {

    override fun getAllStoreData(storeId: Int): Observable<StoreData> {
        return api.getDataForStore(
            WebService = "ConsultaIntegralTienda",
            IdTienda = storeId.toString()
        ).toObservable()
    }

    override fun subscribeUserToNewsletter(
        userName: String,
        email: String,
        storeId: Int
    ): Observable<ResponseData> {

        return api.subscribeUserToNewsletter(
            WebService = "GuardaSuscriptor",
            IdTienda = storeId.toString(),
            Nombre = userName,
            Correo = email
        ).toObservable()
    }
}