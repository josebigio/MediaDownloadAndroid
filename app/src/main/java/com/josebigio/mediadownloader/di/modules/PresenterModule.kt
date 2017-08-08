package com.josebigio.mediadownloader.di.modules

import android.app.Activity
import android.content.Context
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.di.PerActivity
import com.josebigio.mediadownloader.managers.DownloadManager
import com.josebigio.mediadownloader.mappers.CommentMapper
import com.josebigio.mediadownloader.mappers.ItemInfoMapper
import com.josebigio.mediadownloader.presenters.DetailsPresenter
import com.josebigio.mediadownloader.presenters.SearchPresenter
import dagger.Module
import dagger.Provides
import timber.log.Timber

/**
 * Created by josebigio on 8/1/17.
 */
@Module
class PresenterModule {

    @PerActivity
    @Provides
    fun provideSearchPresenter(api: ApiManager): SearchPresenter {
        Timber.d("[-DI-] creating SearchPresenter")
        return SearchPresenter(api)
    }

    @PerActivity
    @Provides
    fun provideDetailsPresenter(api: ApiManager, commentMapper: CommentMapper, itemInfoMapper: ItemInfoMapper, downloadManager: DownloadManager): DetailsPresenter {
        Timber.d("[-DI-] creating DetailsPresenter")
        return DetailsPresenter(api,commentMapper,itemInfoMapper,downloadManager)
    }
}