package com.josebigio.mediadownloader

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.josebigio.mediadownloader.views.activities.SearchActivity

/**
 * Created by josebigio on 8/2/17.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, SearchActivity::class.java))
    }
}