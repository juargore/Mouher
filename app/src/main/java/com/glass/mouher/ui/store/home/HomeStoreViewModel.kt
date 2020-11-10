package com.glass.mouher.ui.store.home

import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.domain.entities.ShortProductUI
import com.glass.domain.entities.TopBannerUI
import com.glass.domain.usecases.product.IProductUseCase
import com.glass.domain.usecases.store.IStoreUseCase
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.ui.common.completeUrlForImage
import com.glass.mouher.ui.common.completeUrlForImageOnStore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeStoreViewModel(
    private val context: Context,
    private val storeUseCase: IStoreUseCase,
    private val productUseCase: IProductUseCase
): BaseViewModel(), ClickHandler<AStoreCategoryViewModel> {

    private var storeId = "1"

    @Bindable
    var onClick: Unit? = null

    @Bindable
    var items: List<AStoreCategoryViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    @Bindable
    var bannerList: List<TopBannerUI> = listOf()

    @Bindable
    var itemsNewProducts: List<ShortProductUI> = listOf()

    @Bindable
    var itemsLinkedStores = mutableListOf<Item>()

    @Bindable
    var urlVideo = ""

    @Bindable
    var urlImageVideo = ""


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        addDisposable(storeUseCase.getStoreData(storeId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

            .flatMap {
                return@flatMap storeUseCase.getTopBannerList()
            }
            .flatMap { list ->
                onTopBannerListResponse(list)
                return@flatMap storeUseCase.getImageVideo()
            }

            .flatMap {  urlImageVideo ->
                onUrlImageVideoResponse(urlImageVideo)
                return@flatMap storeUseCase.getUrlVideo()
            }.subscribe(this::onUrlVideoResponse, this::onError)
        )

        addDisposable(productUseCase.getNewArrivalsForStore(storeId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onShortResponseProductUI, this::onError))


        val categoriesList = mutableListOf<Item>()
        categoriesList.add(Item(name = "Accesorios", description = "Complementa tu estilo", imageUrl = "https://static.zara.net/photos//mkt/spots/aw20-north-shoes-bags-woman/subhome-xmedia-33//landscape_0.jpg?ts=1597317424891&imwidth=1366"))
        categoriesList.add(Item(name = "Cremas", description = "Cuida tu piel", imageUrl = "https://static.pullandbear.net/2/static2/itxwebstandard/images/home/2020-07/31/1400/Newin_Woman.jpg?ver=20200813112500"))
        categoriesList.add(Item(name = "Hogar", description = "Todo para tu hogar", imageUrl = "https://static.zara.net/photos///2020/I/1/1/p/6660/510/040/3/w/1337/6660510040_9_1_1.jpg?ts=1597259304529"))
        categoriesList.add(Item(name = "Oficina", description = "Trabaja como te gusta", imageUrl = "https://static.zara.net/photos///rw-center/2020/I/0/1/p/1856/209/881/2/w/1337/1856209881_2_11_1.jpg?ts=1597061763237"))
        categoriesList.add(Item(name = "Accesorios", description = "Complementa tu estilo", imageUrl = "https://static.zara.net/photos//mkt/spots/aw20-north-shoes-bags-woman/subhome-xmedia-33//landscape_0.jpg?ts=1597317424891&imwidth=1366"))
        categoriesList.add(Item(name = "Cremas", description = "Cuida tu piel", imageUrl = "https://static.pullandbear.net/2/static2/itxwebstandard/images/home/2020-07/31/1400/Newin_Woman.jpg?ver=20200813112500"))

        val viewModels = mutableListOf<AStoreCategoryViewModel>()

        categoriesList.forEach {
            val viewModel = StoreCategoryItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        items = viewModels


        val newLinkedStoresList = mutableListOf<Item>()
        newLinkedStoresList.add(Item(name = "ZARA", imageUrl = "https://gestion.pe/resizer/sfRLQ14ewsCJLKWqdIMwd93fJ2Q=/980x528/smart/arc-anglerfish-arc2-prod-elcomercio.s3.amazonaws.com/public/LWKXDAO2QZEBBBKVXP76K4RNJI.jpg"))
        newLinkedStoresList.add(Item(name = "PULL & BEAR", imageUrl = "https://i0.wp.com/www.biosferaplaza.es/wp-content/uploads/2015/05/pull-bear.jpg?w=1080"))
        newLinkedStoresList.add(Item(name = "H & M", imageUrl = "https://www.modaes.com/files/2020/empresas/h&m/hm-tienda-en-peru-948.jpg"))
        newLinkedStoresList.add(Item(name = "FOREVER 21", imageUrl = "data:image/jpeg;base64,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"))
        newLinkedStoresList.add(Item(name = "GUCCI", imageUrl = "https://media.gucci.com/content/StoreDetailStandard_970x400/1548145803/StoreDetailStandard_Store-kuwait-entrance_001_Default.jpg"))
        newLinkedStoresList.add(Item(name = "GUESS", imageUrl = "https://www.vallartadaily.com/wp-content/uploads/2018/10/guess-store.jpg"))

        itemsLinkedStores = newLinkedStoresList
        notifyPropertyChanged(BR.itemsLinkedStores)

        //urlVideo = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"
    }

    private fun onShortResponseProductUI(list: List<ShortProductUI>){
        list.forEach {
            it.imageUrl = completeUrlForImageOnStore(it.imageUrl, storeId)
        }

        itemsNewProducts = list
        notifyPropertyChanged(BR.itemsNewProducts)
    }

    private fun onTopBannerListResponse(list: List<TopBannerUI>){
        list.forEach {
            it.imageUrl = completeUrlForImage(it.imageUrl)
        }

        bannerList = list
        notifyPropertyChanged(BR.bannerList)
    }

    private fun onUrlVideoResponse(url: String){
        urlVideo = url
        notifyPropertyChanged(BR.urlVideo)
    }

    private fun onUrlImageVideoResponse(url: String){
        urlImageVideo = completeUrlForImage(url)
        notifyPropertyChanged(BR.urlImageVideo)
    }

    private fun onError(t: Throwable?){
        Log.e("--", t?.localizedMessage.toString())
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

    override fun onClick(viewModel: AStoreCategoryViewModel) {
        if(viewModel is StoreCategoryItemViewModel){
            notifyPropertyChanged(BR.onClick)
        }
    }
}