package com.josebigio.mediadownloader.di.modules

import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.di.PerActivity
import com.josebigio.mediadownloader.managers.DownloadManager
import com.josebigio.mediadownloader.managers.FileManager
import com.josebigio.mediadownloader.mappers.CommentMapper
import com.josebigio.mediadownloader.mappers.ItemInfoMapper
import com.josebigio.mediadownloader.presenters.DetailsPresenter
import com.josebigio.mediadownloader.presenters.LibraryPresenter
import com.josebigio.mediadownloader.presenters.SearchPresenter
import com.josebigio.mediadownloader.providers.CommentsProvider
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
    fun provideDetailsPresenter(api: ApiManager, commentsProvider: CommentsProvider
                                , itemInfoMapper: ItemInfoMapper
                                , fileManager: FileManager): DetailsPresenter {
        Timber.d("[-DI-] creating DetailsPresenter")
        return DetailsPresenter(api,itemInfoMapper,fileManager,commentsProvider)
    }

    @PerActivity
    @Provides
    fun provideLibraryPresenter(fileManager: FileManager): LibraryPresenter {
        Timber.d("[-DI-] creating LibraryPresenter")
        return LibraryPresenter(fileManager)
    }
}