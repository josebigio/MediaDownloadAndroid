package com.josebigio.mediadownloader.di.modules

import android.content.Context
import com.josebigio.mediadownloader.navigation.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by josebigio on 8/2/17.
 */
@Module
class NavigationModule(val context: Context) {

    @Provides
    @Singleton
    fun provideNavigator(context: Context): Navigator {
        return Navigator(context)
    }

}