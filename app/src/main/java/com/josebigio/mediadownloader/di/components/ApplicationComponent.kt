package com.josebigio.mediadownloader.di.components

import android.content.Context
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.di.modules.ApplicationModule
import com.josebigio.mediadownloader.di.modules.ManagerModule
import com.josebigio.mediadownloader.di.modules.NavigationModule
import com.josebigio.mediadownloader.di.modules.NetworkModule
import com.josebigio.mediadownloader.managers.DownloadManager
import com.josebigio.mediadownloader.managers.FileManager
import com.josebigio.mediadownloader.navigation.Navigator
import com.josebigio.mediadownloader.views.activities.BaseActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by josebigio on 8/1/17.
 */
@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        NavigationModule::class,
        ManagerModule::class,
        NetworkModule::class)
)
interface ApplicationComponent {
    fun inject(activity: BaseActivity)

    fun context(): Context
    fun apiManager(): ApiManager
    fun downloadManager(): DownloadManager
    fun fileManager():FileManager
    fun navigator(): Navigator

}