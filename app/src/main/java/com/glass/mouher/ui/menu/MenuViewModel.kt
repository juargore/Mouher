package com.glass.mouher.ui.menu

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.Item
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.usecases.userList.IMenuUseCase
import com.glass.mouher.R
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.common.binder.ClickHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MenuViewModel(
    private val context: Context,
    private val menuUseCase: IMenuUseCase
): BaseViewModel(), ClickHandler<AMenuViewModel> {

    private var source: String? = null

    @Bindable
    var screen: MENU? = null

    @Bindable
    var openProfileScreen: Unit? = null

    @Bindable
    var openHistoryScreen: Unit? = null

    @Bindable
    val userPhoto = R.drawable.face

    @Bindable
    var emptyList: Boolean = false
        set(value){
            field = value
            notifyPropertyChanged(BR.emptyList)
        }

    @Bindable
    var searchProductVisibility: Boolean = false
        set(value){
            field = value
            notifyPropertyChanged(BR.searchProductVisibility)
        }

    @Bindable
    var itemsSocial = mutableListOf<Item>()

    @Bindable
    var items: List<AMenuViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    fun initialize(source: String){
        this.source = source
        searchProductVisibility = source != "MALL"
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        addDisposable(menuUseCase
            .getMenuItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponseMenuItems, this::onError)
        )

        addDisposable(menuUseCase
            .getMenuSocialMediaItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponseMenuSocialMediaItems, this::onError)
        )
    }

    private fun onResponseMenuItems(list: List<Item>){
        val mList = list.toMutableList()
        mList.add(Item( name = "Zona 1"))
        mList.add(Item( name = "Zona 2"))
        mList.add(Item( name = "Zona 3"))
        mList.add(Item( name = "Zona AB"))

        emptyList = mList.isEmpty()

        val viewModels = mutableListOf<AMenuViewModel>()

        mList.forEach {
            val viewModel = MenuItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        items = viewModels
    }

    private fun onResponseMenuSocialMediaItems(list: List<Item>){
        val items = mutableListOf<Item>()
        items.add(Item(imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Facebook_Logo_%282019%29.png/1024px-Facebook_Logo_%282019%29.png"))
        items.add(Item(imageUrl = "https://images.vexels.com/media/users/3/137380/isolated/preview/1b2ca367caa7eff8b45c09ec09b44c16-icono-de-instagram-logo-by-vexels.png"))
        items.add(Item(imageUrl = "https://www.pngkey.com/png/full/2-27646_twitter-logo-png-transparent-background-logo-twitter-png.png"))
        items.add(Item(imageUrl = "https://musicodiy.cdbaby.com/wp-content/uploads/2017/08/2d2700cbc33a006fc7be45736cb80b07-snapchat-icon-logo-by-vexels.png"))

        itemsSocial = items
        notifyPropertyChanged(BR.itemsSocial)
    }

    fun onProfileClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.PROFILE
        notifyPropertyChanged(BR.screen)
    }

    fun onHistoryClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.HISTORY
        notifyPropertyChanged(BR.screen)
    }

    fun onAboutClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.ABOUT
        notifyPropertyChanged(BR.screen)
    }

    fun onContactUsClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.CONTACT
        notifyPropertyChanged(BR.screen)
    }

    fun onExtraServicesClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.EXTRA_SERVICES
        notifyPropertyChanged(BR.screen)
    }

    fun onMoreInfoClick(@Suppress("UNUSED_PARAMETER") view: View){
        screen = MENU.MORE_INFO
        notifyPropertyChanged(BR.screen)
    }

    private fun onError(t: Throwable?){
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        Log.e("ERROR", t?.localizedMessage)
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

    override fun onClick(viewModel: AMenuViewModel) {
        if(viewModel is MenuItemViewModel){
            Log.e("--", "${viewModel.name}")

            /*screen = when(viewModel.name){
                "Mi Perfil"-> MENU.PROFILE
                "Mis compras"-> MENU.HISTORY
                "Acerca de" -> MENU.ABOUT
                else -> MENU.MORE_INFO
            }

            notifyPropertyChanged(BR.screen)*/
        }
    }
}

enum class MENU{
    PROFILE,
    HISTORY,
    ABOUT,
    CONTACT,
    EXTRA_SERVICES,
    MORE_INFO
}