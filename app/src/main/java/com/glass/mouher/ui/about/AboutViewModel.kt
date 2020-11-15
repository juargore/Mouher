package com.glass.mouher.ui.about

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.AboutPersonUI
import com.glass.domain.entities.AboutUI
import com.glass.domain.usecases.mall.IMallUseCase
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

class AboutViewModel(
    private val context: Context,
    private val mallUseCase: IMallUseCase
): BaseViewModel() {

    @Bindable
    var urlTopImage = ""

    @Bindable
    var textTopOnImage = ""

    @Bindable
    var title = ""

    @Bindable
    var subtitle = ""

    @Bindable
    var description = ""

    @Bindable
    var peopleList: List<AboutPersonUI> = listOf()


    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }


    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        progressVisible = true
        addDisposable(mallUseCase.getAboutInformation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onAboutResponse, this::onError))
    }

    private fun onAboutResponse(response: AboutUI){
        response.let{
            urlTopImage = it.topImageUrl ?: ""
            textTopOnImage = it.topText ?: ""
            title = it.title ?: ""
            subtitle = it.subtitle ?: ""
            description = it.description ?: ""
            peopleList = it.peopleList ?: emptyList()

            notifyPropertyChanged(BR.urlTopImage)
            notifyPropertyChanged(BR.textTopOnImage)
            notifyPropertyChanged(BR.title)
            notifyPropertyChanged(BR.subtitle)
            notifyPropertyChanged(BR.description)
            notifyPropertyChanged(BR.peopleList)
        }

        progressVisible = false
    }

    private fun onError(t: Throwable?){
        progressVisible = false
        context.toast(t?.localizedMessage.toString())
    }

    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}