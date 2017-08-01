package com.josebigio.mediadownloader

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.josebigio.mediadownloader.di.components.DaggerNetworkComponent
import com.josebigio.mediadownloader.di.components.NetworkComponent
import com.josebigio.mediadownloader.di.modules.NetworkModule
import timber.log.Timber
/**
 * Created by josebigio on 7/28/17.
 */
class MediaApplication: Application() {

    companion object {
        lateinit var networkComponent: NetworkComponent
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this);
        networkComponent = DaggerNetworkComponent
                .builder()
                .networkModule(NetworkModule(this))
                .build()

    }
}