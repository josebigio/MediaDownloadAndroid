package com.josebigio.mediadownloader.di.modules


import com.josebigio.mediadownloader.MediaApplication
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.api.AudioApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by josebigio on 7/30/17.
 */
@Module
class NetworkModule(val app:MediaApplication) {

    @Provides
    @Singleton
    fun provideApiManager(): ApiManager {
                val retrofit = Retrofit.Builder()
                .baseUrl("http://ec2-52-11-161-241.us-west-2.compute.amazonaws.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return ApiManager(retrofit.create(AudioApi::class.java))
    }

}