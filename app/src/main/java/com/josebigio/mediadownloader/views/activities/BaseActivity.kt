package com.josebigio.mediadownloader.views.activities

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
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
abstract class BaseActivity : AppCompatActivity() {

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
        val drawerContainer = layoutInflater.inflate(R.layout.drawer_container_view, null)
        val viewContainer = drawerContainer.findViewById<ViewGroup>(R.id.content_frame)
        layoutInflater.inflate(layoutResID, viewContainer, true)
        super.setContentView(drawerContainer)
        initializeDrawerNavigation()
        initializeActionBar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchToolbar -> {
                navigator.navigateToSearch(this)
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    protected abstract fun selectDrawerPos()

    override fun onResume() {
        super.onResume()
        selectDrawerPos()
    }

    private fun initializeDrawerNavigation() {
        navigationView.setNavigationItemSelectedListener { item ->
            drawer.closeDrawer(navigationView)
            if(item.isChecked) {
                return@setNavigationItemSelectedListener false
            }
            when (item.itemId) {
                R.id.library -> {
                    navigator.navigateToLibrary(this@BaseActivity)
                    return@setNavigationItemSelectedListener false
                }
                else -> {
                    return@setNavigationItemSelectedListener false
                }
            }
        }

    }



    private fun initializeActionBar() {
        val toolBar = findViewById<Toolbar>(R.id.mainToolbar)
        setSupportActionBar(toolBar)
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer, toolBar, R.string.openDrawer, R.string.closeDrawer) {

            override fun onDrawerClosed(drawerView: View) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView)
            }
        }
        drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

    }

    private fun getImageUri(): Uri {
        return Uri.parse("android.resource://com.josebigio.mediadownloader/drawable/hercules_cartoon")
    }


}