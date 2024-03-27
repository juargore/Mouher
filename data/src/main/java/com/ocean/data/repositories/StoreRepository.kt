package com.ocean.data.repositories

import com.ocean.data.serverapi.StoreApi
import com.ocean.domain.entities.ResponseData
import com.ocean.domain.entities.StoreData
import com.ocean.domain.repositories.IStoreRepository
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