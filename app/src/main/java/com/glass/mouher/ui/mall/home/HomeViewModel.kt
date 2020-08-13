package com.glass.mouher.ui.mall.home

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import com.glass.domain.usecases.categories.ICategoriesUseCase
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeViewModel(
    private val context: Context,
    private val categoriesUseCase: ICategoriesUseCase
): BaseViewModel() {

    @Bindable
    var bannerList = mutableListOf<Item>()

    @Bindable
    var sponsorsList = mutableListOf<Item>()

    @Bindable
    var zonesList = mutableListOf<Item>()

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val disposable = categoriesUseCase.getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponse, this::onError)

        addDisposable(disposable)
    }

    private fun onResponse(list: List<Item>){

        bannerList.add(Item(imageUrl = "https://d500.epimg.net/cincodias/imagenes/2020/05/23/companias/1590247574_823229_1590247687_noticia_normal.jpg"))
        bannerList.add(Item(imageUrl = "https://www.modaes.com/files/000_2016/mexico/Mexico%20centro%20comercial%20Santa%20Fe%20728.png"))
        bannerList.add(Item(imageUrl = "https://s03.s3c.es/imag/_v0/770x420/7/c/b/centro-comercial-770.jpg"))
        bannerList.add(Item(imageUrl = "https://cdn.cnn.com/cnnnext/dam/assets/190723081122-01-mall-of-america-minneapolis-interior-stock-exlarge-169.jpg"))
        notifyPropertyChanged(BR.bannerList)

        sponsorsList.add(Item(imageUrl = "https://i.pinimg.com/originals/75/b7/59/75b759a40bb58ac5afbdaea57455831d.jpg"))
        sponsorsList.add(Item(imageUrl = "https://bcassetcdn.com/public/blog/wp-content/uploads/2019/07/18094726/artisan-oz.jpg"))
        sponsorsList.add(Item(imageUrl = "https://i.etsystatic.com/10773810/r/il/5bda90/1718025006/il_570xN.1718025006_3wes.jpg"))
        sponsorsList.add(Item(imageUrl = "https://mir-s3-cdn-cf.behance.net/project_modules/1400/6cbf3568556191.5b611f672d5e3.jpg"))
        notifyPropertyChanged(BR.sponsorsList)

        zonesList.add(Item(name = "Zona 1", imageUrl = "https://c8.alamy.com/comp/PJ3E6W/fashion-shopping-girl-portrait-beauty-woman-with-shopping-bags-in-shopping-mall-shopper-sales-PJ3E6W.jpg", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona 2", imageUrl = "data:image/jpeg;base64,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", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona 3", imageUrl = "https://thumbs.dreamstime.com/z/woman-shopping-mall-portrait-young-bag-31002956.jpg", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona A", imageUrl = "https://images.assetsdelivery.com/compings_v2/deagreez/deagreez1704/deagreez170400054.jpg", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona B", imageUrl = "https://i.pinimg.com/736x/2e/3e/4c/2e3e4c8ab77ef32eba3699b6dfe8aa5f.jpg", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona C", imageUrl = "https://static1.bigstockphoto.com/5/4/2/large1500/245068216.jpg", description = "Descripción zona"))
        notifyPropertyChanged(BR.zonesList)
    }

    private fun onError(t: Throwable?){
        t?.let {

        }
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
        bannerList.clear()
    }
}