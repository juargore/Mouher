package com.glass.mouher.ui.menu

import android.content.Context
import android.util.Log
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

    @Bindable
    val userPhoto = R.drawable.face

    @Bindable
    var emptyList: Boolean = false
        set(value){
            field = value
            notifyPropertyChanged(BR.emptyList)
        }

    @Bindable
    var items: List<AMenuViewModel> = listOf()
        set(value){
            field = value
            notifyPropertyChanged(BR.items)
        }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        val disposable = menuUseCase.getMenuItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onResponse, this::onError)

        addDisposable(disposable)
    }

    private fun onResponse(list: List<Item>){
        val mList = list.toMutableList()
        mList.add(Item( name = "Promociones", icon = R.drawable.ic_new_releases))
        mList.add(Item( name = "Enlaces patrocinados", icon = R.drawable.ic_accessibility))
        mList.add(Item( name = "Nuevas llegadas", icon = R.drawable.ic_gift))
        mList.add(Item( name = "Afíliate con nosotros", icon = R.drawable.ic_work))
        mList.add(Item( name = "Acerca de", icon = R.drawable.ic_info))

        emptyList = mList.isEmpty()

        val viewModels = mutableListOf<AMenuViewModel>()

        mList.forEach {
            val viewModel = MenuItemViewModel(context = context, menu = it)
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

    override fun onClick(viewModel: AMenuViewModel) {
        if(viewModel is MenuItemViewModel){
            Log.e("--", "${viewModel.name}")
        }
    }
}