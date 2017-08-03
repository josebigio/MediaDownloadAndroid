package com.josebigio.mediadownloader.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by josebigio on 8/2/17.
 */
@Module
class ApplicationModule(val application: Application){

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }
}