package com.josebigio.mediadownloader.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.josebigio.mediadownloader.MediaApplication
import com.josebigio.mediadownloader.di.modules.ActivityModule

/**
 * Created by josebigio on 8/2/17.
 */
abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MediaApplication.mainComponent.inject(this)
    }

    protected fun getActivityModule(): ActivityModule {
        return ActivityModule(this)
    }

}