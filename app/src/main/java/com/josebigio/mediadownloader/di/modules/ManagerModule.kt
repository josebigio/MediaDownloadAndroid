package com.josebigio.mediadownloader.di.modules

import android.content.Context
import com.josebigio.mediadownloader.managers.DownloadManager
import com.josebigio.mediadownloader.managers.FileManager
import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by josebigio on 8/6/17.
 */
@Module
class ManagerModule {

    @Provides
    @Singleton
    fun provideDownloadManager(context: Context): DownloadManager {
        Timber.d("[-DI-] creating DownloadManager")
        return DownloadManager(context)
    }

    @Provides
    @Singleton
    fun provideFileManager(context: Context): FileManager {
        Timber.d("[-DI-] creating FileManager")
        return FileManager(context)
    }
}