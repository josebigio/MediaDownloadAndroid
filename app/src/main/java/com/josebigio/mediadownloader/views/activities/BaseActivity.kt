package com.josebigio.mediadownloader.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.josebigio.mediadownloader.MediaApplication
import com.josebigio.mediadownloader.di.components.ActivityComponent
import com.josebigio.mediadownloader.di.components.DaggerActivityComponent
import com.josebigio.mediadownloader.di.modules.ActivityModule

/**
 * Created by josebigio on 8/2/17.
 */
abstract class BaseActivity: AppCompatActivity() {

    protected lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(MediaApplication.applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
    }



}