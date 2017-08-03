package com.josebigio.mediadownloader.di.modules

import android.content.Context
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.navigation.Navigator
import com.josebigio.mediadownloader.presenters.SearchPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by josebigio on 8/1/17.
 */
@Module
class PresenterModule(val context: Context) {

    @Provides
    @Singleton
    fun provideSearchPresenter(api:ApiManager, navigator: Navigator): SearchPresenter {
        return SearchPresenter(api,navigator)
    }

}