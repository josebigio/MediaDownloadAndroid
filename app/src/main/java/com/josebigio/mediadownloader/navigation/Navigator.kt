package com.josebigio.mediadownloader.navigation

import android.content.Context
import com.josebigio.mediadownloader.views.activities.DetailsActivity
import com.josebigio.mediadownloader.views.activities.LibraryActivity

/**
 * Created by josebigio on 8/2/17.
 */
class Navigator {

    fun navigateToDetails(id: String, context: Context) {
        context.startActivity(DetailsActivity.getCallingIntent(context,id))
    }

    fun navigateToLibrary(context: Context) {
        context.startActivity(LibraryActivity.getCallingIntent(context))
    }

}


