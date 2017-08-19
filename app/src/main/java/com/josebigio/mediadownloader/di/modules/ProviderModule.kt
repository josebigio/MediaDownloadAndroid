package com.josebigio.mediadownloader.di.modules

import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.di.PerActivity
import com.josebigio.mediadownloader.mappers.CommentMapper
import com.josebigio.mediadownloader.providers.CommentsProvider
import dagger.Module
import dagger.Provides
import timber.log.Timber

/**
 * Created by josebigio on 8/17/17.
 */
@Module
class ProviderModule {

    @PerActivity
    @Provides
    fun provideCommentProvider(api: ApiManager, commentMapper: CommentMapper): CommentsProvider {
        Timber.d("[-DI-] creating CommentsProvider")
        return CommentsProvider(api,commentMapper)
    }
}