package com.josebigio.mediadownloader.di.components

import com.josebigio.mediadownloader.di.PerActivity
import com.josebigio.mediadownloader.di.modules.ActivityModule
import com.josebigio.mediadownloader.di.modules.NavigationModule
import com.josebigio.mediadownloader.views.activities.SearchActivity
import dagger.Component

/**
 * Created by josebigio on 8/2/17.
 */
@PerActivity
@Component(dependencies = arrayOf(MainComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: SearchActivity)

}