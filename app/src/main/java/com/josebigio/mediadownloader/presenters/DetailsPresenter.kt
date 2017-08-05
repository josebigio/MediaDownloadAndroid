package com.josebigio.mediadownloader.presenters

import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.models.ItemInfo
import com.josebigio.mediadownloader.navigation.Navigator
import com.josebigio.mediadownloader.views.interfaces.DetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by josebigio on 8/3/17.
 */
@Singleton
class DetailsPresenter(val api: ApiManager, val navigator: Navigator) {

    private var view: DetailsView? = null
    private var id: String? = null

    fun onViewActive() {
        Timber.d("onViewActive")
    }

    fun onViewInactive() {
        Timber.d("onViewInactive")
    }

    fun initialize(id: String, view: DetailsView) {
        this.id = id
        this.view = view
        api.getInfo(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    infoResponse ->
                    val result = infoResponse.results
                    Timber.d("getInfoApi: $result")
                    view.render(ItemInfo(result.title,
                            result.description,
                            result.duration,
                            result.thumbnails.high.url))
                })
    }


}