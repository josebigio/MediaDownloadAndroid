package com.josebigio.mediadownloader.di.components

import com.josebigio.mediadownloader.MainActivity
import com.josebigio.mediadownloader.di.modules.NetworkModule
import com.josebigio.mediadownloader.views.SearchFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by josebigio on 7/30/17.
 */
@Singleton
@Component(modules = arrayOf(
        NetworkModule::class)
)
interface NetworkComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: SearchFragment)

}