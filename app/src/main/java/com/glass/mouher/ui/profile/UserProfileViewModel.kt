package com.glass.mouher.ui.profile

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.Item
import com.glass.domain.usecases.userProfile.IUserProfileUseCase
import com.glass.mouher.R
import com.glass.mouher.ui.base.BaseViewModel
import com.glass.mouher.ui.menu.AMenuViewModel
import com.glass.mouher.ui.menu.MenuItemViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserProfileViewModel(
    private val context: Context,
    private val userProfileUseCase: IUserProfileUseCase
): BaseViewModel() {

    @Bindable
    val userPhoto = R.drawable.face

    @Bindable
    var items: List<AUserProfileViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val disposable = userProfileUseCase.getUserProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponse, this::onError)

        addDisposable(disposable)
    }

    private fun onResponse(user: Item){

        val mList = mutableListOf<Item>()
        mList.add(Item( name = "Administrar mi perfil de usuario", imageUrl = "https://image.flaticon.com/icons/png/512/130/130304.png"))
        mList.add(Item( name = "Dirección de entrega", imageUrl = "https://cdn2.iconfinder.com/data/icons/ios-7-icons/50/user_male2-512.png"))
        mList.add(Item( name = "Información bancaria", imageUrl = "https://icon-library.com/images/statistic-icon/statistic-icon-0.jpg"))
        mList.add(Item( name = "Mis compras realizadas", imageUrl = "https://cdn.onlinewebfonts.com/svg/img_556420.png"))

        val viewModels = mutableListOf<AUserProfileViewModel>()

        mList.forEach {
            val viewModel = UserProfileItemViewModel(context = context, menu = it)
            viewModels.add(viewModel)
        }

        items = viewModels
    }

    private fun onError(t: Throwable?){

    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
        onCleared()
    }

}