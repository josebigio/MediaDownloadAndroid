package com.josebigio.mediadownloader.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.models.MediaFile
import com.josebigio.mediadownloader.presenters.LibraryPresenter
import com.josebigio.mediadownloader.views.adapters.LibraryAdapter
import com.josebigio.mediadownloader.views.interfaces.LibraryView
import kotlinx.android.synthetic.main.drawer_container_view.*
import kotlinx.android.synthetic.main.library_view.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by josebigio on 8/2/17.
 */
class LibraryActivity: BaseActivity(), LibraryView, LibraryAdapter.LibraryAdapterDelegate {

    @Inject
    lateinit var presenter: LibraryPresenter

    val libraryAdapter = LibraryAdapter(this)

    companion object {
        fun getCallingIntent(context: Context): Intent {
           val intent = Intent(context, LibraryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_view)
        activityComponent.inject(this)
        initializeViews()
        presenter.initialize(this)
    }

    override fun renderItems(files: List<MediaFile>) {
        Timber.d("Rendering files: $files")
        libraryAdapter.dataSet = files
        libraryAdapter.notifyDataSetChanged()
    }

    override fun onItemClicked(file: MediaFile) {
        presenter.onItemClicked(file)
    }

    override fun navigateToDetails(id:String) {
        navigator.navigateToDetails(id,this)
    }

    override fun selectDrawerPos() {
        navigationView.setCheckedItem(R.id.library)
    }

    private fun initializeViews() {
        libraryRecycler.layoutManager = LinearLayoutManager(this)
        libraryRecycler.adapter = libraryAdapter
    }

}