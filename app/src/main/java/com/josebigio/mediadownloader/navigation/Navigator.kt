package com.josebigio.mediadownloader.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import javax.inject.Singleton

/**
 * Created by josebigio on 8/2/17.
 */
class Navigator(val context: Context) {



    fun navigateTo(page: Page) {
//        when (page) {
//            Page.SEARCH ->
//        }
    }

    private fun goToView() {
        val intent = Intent()
   }
}

enum class Page {
    SEARCH,
    DETAILS,
    LIBRARY,
}