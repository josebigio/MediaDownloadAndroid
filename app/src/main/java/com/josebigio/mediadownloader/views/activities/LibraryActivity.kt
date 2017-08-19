package com.josebigio.mediadownloader.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.constants.DETAIL_VIEW_ID_KEY

/**
 * Created by josebigio on 8/2/17.
 */
class LibraryActivity: BaseActivity() {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val callingIntent = Intent(context, LibraryActivity::class.java)
            return callingIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_view)
        activityComponent.inject(this)
    }


}