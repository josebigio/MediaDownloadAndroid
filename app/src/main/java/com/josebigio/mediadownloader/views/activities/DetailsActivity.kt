package com.josebigio.mediadownloader.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.josebigio.mediadownloader.MediaApplication
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.constants.DETAIL_VIEW_ID_KEY
import com.josebigio.mediadownloader.di.components.ActivityComponent
import com.josebigio.mediadownloader.di.components.DaggerActivityComponent
import com.josebigio.mediadownloader.models.ItemInfo
import com.josebigio.mediadownloader.presenters.DetailsPresenter
import com.josebigio.mediadownloader.views.adapters.SearchItem
import com.josebigio.mediadownloader.views.interfaces.DetailsView
import kotlinx.android.synthetic.main.details_view.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by josebigio on 8/2/17.
 */
class DetailsActivity: BaseActivity(), DetailsView {

    companion object {
        fun getCallingIntent(context: Context, id: String): Intent {
            val callingIntent = Intent(context, DetailsActivity::class.java)
            callingIntent.putExtra(DETAIL_VIEW_ID_KEY, id)
            return callingIntent
        }
    }

    @Inject
    @Singleton
    lateinit var presenter: DetailsPresenter

    lateinit var activityComponent: ActivityComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_view)
        val mediaApp = MediaApplication.mainComponent
        activityComponent = DaggerActivityComponent.builder()
                .mainComponent(mediaApp)
                .activityModule(getActivityModule())
                .build()
        activityComponent.inject(this)
        initializePresenter(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewActive()
    }


    override fun onPause() {
        super.onPause()
        presenter.onViewInactive()
    }


    override fun render(itemInfo: ItemInfo) {
        Timber.d("rendering item: $itemInfo")
        detailsTitleTV.text = itemInfo.title
        detailsDescriptionTV.text = itemInfo.description
        detailsDraweeView.setImageURI(itemInfo.imageUrl)
    }

    override fun showLoading(show: Boolean) {
    }


    private fun initializePresenter(savedInstanceState: Bundle?) {
        val id:String?
        if (savedInstanceState == null) {
            id = intent.getStringExtra(DETAIL_VIEW_ID_KEY)
        } else {
            id = savedInstanceState.getString(DETAIL_VIEW_ID_KEY)
        }
        if(id != null) presenter.initialize(id,this)
    }

}