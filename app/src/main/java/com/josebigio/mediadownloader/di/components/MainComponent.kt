package com.josebigio.mediadownloader.di.components

import android.app.Application
import android.content.Context
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.di.modules.ApplicationModule
import com.josebigio.mediadownloader.di.modules.NavigationModule
import com.josebigio.mediadownloader.di.modules.NetworkModule
import com.josebigio.mediadownloader.di.modules.PresenterModule
import com.josebigio.mediadownloader.navigation.Navigator
import com.josebigio.mediadownloader.presenters.SearchPresenter
import com.josebigio.mediadownloader.views.activities.BaseActivity
import com.josebigio.mediadownloader.views.activities.SearchActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by josebigio on 8/1/17.
 */
@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        PresenterModule::class,
        NavigationModule::class,
        NetworkModule::class)
)
interface MainComponent  {
    fun inject(activity: BaseActivity)

    fun context(): Context
    fun searchPresenter() : SearchPresenter
    fun apiManager(): ApiManager
    fun navigator(): Navigator

}