package com.josebigio.mediadownloader.di.components

import com.josebigio.mediadownloader.di.modules.NetworkModule
import com.josebigio.mediadownloader.di.modules.PresenterModule
import com.josebigio.mediadownloader.views.SearchFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by josebigio on 8/1/17.
 */
@Singleton
@Component(modules = arrayOf(
        PresenterModule::class,
        NetworkModule::class)
)
interface MainComponent  {
    fun inject(activity: SearchFragment)
}