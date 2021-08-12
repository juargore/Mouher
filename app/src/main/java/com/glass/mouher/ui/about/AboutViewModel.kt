@file:Suppress("DEPRECATION", "UNUSED_PARAMETER")
package com.glass.mouher.ui.about

import android.text.Html
import android.text.Spanned
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.glass.domain.entities.AboutPersonUI
import com.glass.domain.entities.AboutUI
import com.glass.domain.usecases.mall.IMallUseCase
import com.glass.mouher.BR
import com.glass.mouher.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AboutViewModel(
    private val mallUseCase: IMallUseCase
): BaseViewModel() {

    private var storeId = 0

    @Bindable
    var urlTopImage = ""

    @Bindable
    var textTopOnImage = ""

    @Bindable
    var title = ""

    @Bindable
    var subtitle = ""

    @Bindable
    var description: Spanned? = null

    @Bindable
    var peopleList: List<AboutPersonUI> = listOf()

    @Bindable
    var hidePeopleSection = false

    @Bindable
    var progressVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisible)
        }

    fun initialize(storId: Int){
        storeId = storId
    }

    override fun onResume(callback: Observable.OnPropertyChangedCallback?) {
        addOnPropertyChangedCallback(callback)

        progressVisible = true

        // if storeId == 0 we are in the Mall App; if storeId != 0 we are in the Store Application
        addDisposable(mallUseCase.getAboutInformation(if(storeId != 0) storeId else null)
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
            description = Html.fromHtml(it.description)
            peopleList = it.peopleList ?: emptyList()

            notifyPropertyChanged(BR.urlTopImage)
            notifyPropertyChanged(BR.textTopOnImage)
            notifyPropertyChanged(BR.title)
            notifyPropertyChanged(BR.subtitle)
            notifyPropertyChanged(BR.description)
            notifyPropertyChanged(BR.peopleList)

            // show the partner section for mall or hide it for store
            with(peopleList){
                if(this[0].name.isNullOrBlank() &&
                   this[1].name.isNullOrBlank() &&
                   this[2].name.isNullOrBlank()){

                   hidePeopleSection = true
                   notifyPropertyChanged(BR.hidePeopleSection)
                }
            }
        }

        progressVisible = false
    }


    private fun onError(t: Throwable?){
        progressVisible = false
    }


    override fun onPause(callback: Observable.OnPropertyChangedCallback?) {
        removeOnPropertyChangedCallback(callback)
    }
}