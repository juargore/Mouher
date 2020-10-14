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
    var lobbyList = mutableListOf<Item>()

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

    private fun onResponse(@Suppress("UNUSED_PARAMETER") list: List<Item>){

        bannerList.add(Item(imageUrl = "https://d500.epimg.net/cincodias/imagenes/2020/05/23/companias/1590247574_823229_1590247687_noticia_normal.jpg", name = "Bienvenido", description = "Mouher Market"))
        bannerList.add(Item(imageUrl = "https://www.modaes.com/files/000_2016/mexico/Mexico%20centro%20comercial%20Santa%20Fe%20728.png", name = "Habilita tu \ne-commerce", description = "Cuanto antes"))
        bannerList.add(Item(imageUrl = "https://s03.s3c.es/imag/_v0/770x420/7/c/b/centro-comercial-770.jpg", name = "Titulo", description = "Descripcion"))
        bannerList.add(Item(imageUrl = "https://cdn.cnn.com/cnnnext/dam/assets/190723081122-01-mall-of-america-minneapolis-interior-stock-exlarge-169.jpg", name = "Otro titulo", description = "Otra descripcion"))
        notifyPropertyChanged(BR.bannerList)

        sponsorsList.add(Item(imageUrl = "https://i.pinimg.com/originals/75/b7/59/75b759a40bb58ac5afbdaea57455831d.jpg"))
        sponsorsList.add(Item(imageUrl = "https://bcassetcdn.com/public/blog/wp-content/uploads/2019/07/18094726/artisan-oz.jpg"))
        sponsorsList.add(Item(imageUrl = "https://i.etsystatic.com/10773810/r/il/5bda90/1718025006/il_570xN.1718025006_3wes.jpg"))
        sponsorsList.add(Item(imageUrl = "https://mir-s3-cdn-cf.behance.net/project_modules/1400/6cbf3568556191.5b611f672d5e3.jpg"))
        notifyPropertyChanged(BR.sponsorsList)

        lobbyList.add(Item(imageUrl = "https://d500.epimg.net/cincodias/imagenes/2020/05/23/companias/1590247574_823229_1590247687_noticia_normal.jpg", name = "Bienvenido", description = "Mouher Market"))
        lobbyList.add(Item(imageUrl = "https://www.modaes.com/files/000_2016/mexico/Mexico%20centro%20comercial%20Santa%20Fe%20728.png", name = "Habilita tu \ne-commerce", description = "Cuanto antes"))
        lobbyList.add(Item(imageUrl = "https://s03.s3c.es/imag/_v0/770x420/7/c/b/centro-comercial-770.jpg", name = "Titulo", description = "Descripcion"))
        notifyPropertyChanged(BR.lobbyList)

        zonesList.clear()
        zonesList.add(Item(name = "Zona 1", imageUrl = "https://image.freepik.com/free-photo/shopping-concept-close-up-portrait-young-beautiful-attractive-redhair-girl-smiling-looking-camera-with-shopping-bag-blue-pastel-background-copy-space_1258-856.jpg", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona 2", imageUrl = "https://image.shutterstock.com/image-photo/portrait-young-happy-smiling-woman-260nw-392415220.jpg", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona 3", imageUrl = "https://img.freepik.com/free-photo/portrait-pretty-woman-dress-holding-mobile-phone_171337-6983.jpg?size=626&ext=jpg&ga=GA1.2.186962824.1594857600", description = "Descripción zona"))
        //zonesList.add(Item(name = "Zona A", imageUrl = "https://previews.123rf.com/images/siraphol/siraphol2005/siraphol200501702/147316500-portrait-beautiful-young-asian-woman-shopping-grocery-from-supermarket-and-cart-on-yellow-isolated-b.jpg", description = "Descripción zona"))
        //zonesList.add(Item(name = "Zona B", imageUrl = "https://image.shutterstock.com/image-photo/portrait-african-american-woman-carrying-260nw-547684396.jpg", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona A", description = "Descripción zona"))
        zonesList.add(Item(name = "Zona B", description = "Descripción zona"))
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