package com.josebigio.mediadownloader

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.josebigio.mediadownloader.di.components.DaggerMainComponent
import com.josebigio.mediadownloader.di.components.MainComponent
import com.josebigio.mediadownloader.di.modules.ApplicationModule
import com.josebigio.mediadownloader.di.modules.NavigationModule
import com.josebigio.mediadownloader.di.modules.NetworkModule
import com.josebigio.mediadownloader.di.modules.PresenterModule
import timber.log.Timber
/**
 * Created by josebigio on 7/28/17.
 */
class MediaApplication: Application() {

    companion object {
        lateinit var mainComponent: MainComponent

    }

    override fun onCreate() {
         super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)
        mainComponent = DaggerMainComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .navigationModule(NavigationModule(this))
                .networkModule(NetworkModule(this))
                .presenterModule(PresenterModule(this))
                .build()

    }


}