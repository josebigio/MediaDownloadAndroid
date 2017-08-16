package com.josebigio.mediadownloader

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.josebigio.mediadownloader.di.components.ApplicationComponent
import com.josebigio.mediadownloader.di.components.DaggerApplicationComponent
import com.josebigio.mediadownloader.di.modules.ApplicationModule
import com.josebigio.mediadownloader.di.modules.NavigationModule
import com.josebigio.mediadownloader.di.modules.NetworkModule
import io.realm.Realm
import timber.log.Timber
import android.os.StrictMode



/**
 * Created by josebigio on 7/28/17.
 */
class MediaApplication: Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent

    }

    override fun onCreate() {
         super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)
        Realm.init(this)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .navigationModule(NavigationModule())
                .networkModule(NetworkModule())
                .build()

    }


}