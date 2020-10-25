package com.glass.data.repositories.firestore

import com.glass.data.repositories.repositories.serverapi.StoreApi
import com.glass.domain.entities.ResponseSponsorStores
import com.glass.domain.entities.SponsorStoreUI
import com.glass.domain.repositories.IStoreRepository
import io.reactivex.Observable

class StoreRepository(
    private val api: StoreApi
): IStoreRepository {

    override fun getSponsorStoresByMall(): Observable<List<SponsorStoreUI>> {
        /*return api.getSponsorStoresByMall(
            WebService = "ConsultaRegEnlacePlazaIdPlaza",
            IdBDD = "0",
            IdPlaza = "1"
        ).flatMap { responseSponsorStores ->

            val list = responseSponsorStores.Datos?

            list.forEach {
                api.getSponsorStoresByMall(
                    WebService = "ConsultaCatTiendaIdZona",
                    IdBDD = "0",
                    IdPlaza = "1"
                )
            }

        }.toObservable()*/
        return Observable.just(emptyList())
    }

}