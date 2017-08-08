package com.josebigio.mediadownloader.di.modules

import android.app.Activity
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.di.PerActivity
import com.josebigio.mediadownloader.presenters.DetailsPresenter
import com.josebigio.mediadownloader.presenters.SearchPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by josebigio on 8/2/17.
 */
@Module
class ActivityModule(val activity: Activity) {

    @PerActivity
    @Provides
    fun provideActivity(): Activity {
        return activity
    }


}