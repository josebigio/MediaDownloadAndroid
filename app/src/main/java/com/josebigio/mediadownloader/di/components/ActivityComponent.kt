package com.josebigio.mediadownloader.di.components

import com.josebigio.mediadownloader.di.PerActivity
import com.josebigio.mediadownloader.di.modules.ActivityModule
import com.josebigio.mediadownloader.di.modules.PresenterModule
import com.josebigio.mediadownloader.views.activities.DetailsActivity
import com.josebigio.mediadownloader.views.activities.SearchActivity
import dagger.Component

/**
 * Created by josebigio on 8/2/17.
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class, PresenterModule::class))
interface ActivityComponent {

    fun inject(activity: SearchActivity)
    fun inject(activity: DetailsActivity)
}