package com.glass.domain.usecases.store

import com.glass.domain.entities.*
import com.glass.domain.repositories.IStoreRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class StoreUseCase(
    private val storeRepository: IStoreRepository
): IStoreUseCase {

    private var storeData: StoreData? = null

    override fun getSponsorStoresByMallId(mallId: String): Observable<List<SponsorUI>> {

        return storeRepository
            .getSponsorStoresByMall()
            .map { listData->
                val nList = mutableListOf<SponsorUI>()

                listData.forEach { store ->
                    nList.add(store.getSponsorStoreUI())
                }
                return@map nList
            }
            .map { listUI->

                listUI.forEach {
                    it.urlImage = getImageForStore(it.id)
                }
                return@map listUI
            }
    }

    @Suppress("CheckResult")
    override fun getStoreData(storeId: String): Observable<String> {
        return storeRepository
            .getAllStoreData(storeId)
            .observeOn(Schedulers.io())
            .map {
                this.storeData = it
                return@map it.IdZona.toString()
            }
    }

    override fun getTopBannerList(): Observable<List<TopBannerUI>> {
        return Observable.just(storeData?.getTopBannerList())
    }

    override fun getStoreLogo(): Observable<String> {
        return Observable.just(storeData?.getStoreLogoImage())
    }

    override fun getImageVideo(): Observable<String> {
        return Observable.just(storeData?.getImageVideo())
    }

    override fun getUrlVideo(): Observable<String> {
        return Observable.just(storeData?.getLinkForVideo())
    }

    override fun getCategoriesByStore(storeId: String): Single<List<CategoryUI>> {
        val mList = mutableListOf<CategoryUI>()
        mList.add(CategoryUI(id = "1", name = "Accesorios", description = "Complementa tu estilo", imageUrl = "https://static.zara.net/photos//mkt/spots/aw20-north-shoes-bags-woman/subhome-xmedia-33//landscape_0.jpg?ts=1597317424891&imwidth=1366"))
        mList.add(CategoryUI(id = "2", name = "Cremas", description = "Cuida tu piel", imageUrl = "https://static.pullandbear.net/2/static2/itxwebstandard/images/home/2020-07/31/1400/Newin_Woman.jpg?ver=20200813112500"))
        mList.add(CategoryUI(id = "3", name = "Hogar", description = "Todo para el hogar", imageUrl = "https://static.zara.net/photos///2020/I/1/1/p/6660/510/040/3/w/1337/6660510040_9_1_1.jpg?ts=1597259304529"))
        mList.add(CategoryUI(id = "4", name = "Oficina", description = "Trabaja desde casa", imageUrl = "https://static.zara.net/photos///rw-center/2020/I/0/1/p/1856/209/881/2/w/1337/1856209881_2_11_1.jpg?ts=1597061763237"))

        return Single.just(mList)
    }

    private fun getImageForStore(storeId: String?): String{
        return storeRepository.getImageForSponsorStore(storeId ?: "0")
            .subscribeOn(Schedulers.io())
            .blockingFirst()
    }

}