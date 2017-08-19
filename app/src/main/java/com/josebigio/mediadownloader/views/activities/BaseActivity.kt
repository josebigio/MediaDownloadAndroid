package com.josebigio.mediadownloader.views.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.FrameLayout
import com.josebigio.mediadownloader.MediaApplication
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.di.components.ActivityComponent
import com.josebigio.mediadownloader.di.components.DaggerActivityComponent
import com.josebigio.mediadownloader.di.modules.ActivityModule
import com.josebigio.mediadownloader.navigation.Navigator
import kotlinx.android.synthetic.main.drawer_container_view.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by josebigio on 8/2/17.
 */
abstract class BaseActivity: AppCompatActivity() {

    protected lateinit var activityComponent: ActivityComponent

    @Singleton
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(MediaApplication.applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
    }

    override fun setContentView(layoutResID: Int) {
        val drawerContainer = layoutInflater.inflate(R.layout.drawer_container_view,null)
        val viewContainer = drawerContainer.findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(layoutResID,viewContainer,true)
        super.setContentView(drawerContainer)
        leftDrawer.setNavigationItemSelectedListener { item ->
            drawer.closeDrawers()
            when (item.itemId) {
                R.id.library -> {
                    navigator.navigateToLibrary(this@BaseActivity)
                    return@setNavigationItemSelectedListener true
                }
                else -> {
                    return@setNavigationItemSelectedListener false
                }
            }
        }

    }



}