package com.josebigio.mediadownloader.views.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ProgressBar
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.constants.DETAIL_VIEW_ID_KEY
import com.josebigio.mediadownloader.models.ItemInfo
import com.josebigio.mediadownloader.models.comments.CommentsModel
import com.josebigio.mediadownloader.presenters.DetailsPresenter
import com.josebigio.mediadownloader.views.adapters.CommentsAdapter
import com.josebigio.mediadownloader.views.interfaces.DetailsView
import jp.wasabeef.fresco.processors.BlurPostprocessor
import kotlinx.android.synthetic.main.details_view.*
import timber.log.Timber
import javax.inject.Inject





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

        val BLUR_RADIUS = 10
    }

    @Inject
    lateinit var presenter: DetailsPresenter

    private var detailsAdapter = CommentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_view)
        activityComponent.inject(this)
        initializeView()
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

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(DETAIL_VIEW_ID_KEY,presenter.id)
        super.onSaveInstanceState(outState)
    }

    override fun renderItemInfo(itemInfo: ItemInfo) {
        Timber.d("rendering item: $itemInfo")
        detailsAdapter.itemInfo = itemInfo
        detailsAdapter.notifyDataSetChanged()
        renderImage(itemInfo.imageUrl)
    }

    override fun renderComments(commentsModel: CommentsModel) {
        detailsAdapter.commentsModel = commentsModel
        detailsAdapter.notifyDataSetChanged()
    }

    override fun showLoading(show: Boolean) {
//        detailsProgress.isIndeterminate = true
//        if (show) detailsProgress.visibility = View.VISIBLE
//        else detailsProgress.visibility = View.GONE

    }

    override fun showProgress(progress:Int) {
        detailsProgress.isIndeterminate = false
        detailsProgress.max = 100
        detailsProgress.progress = progress
        detailsProgress.visibility = View.VISIBLE

        val progressBar = ProgressBar(this)
    }

    override fun enableDownload(enable: Boolean) {
        if(enable) {
            detailsDownloadButton.show()
        }
        else {
            detailsDownloadButton.hide()
        }
    }

    fun renderImage(url: String) {
        val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setPostprocessor(BlurPostprocessor(this, BLUR_RADIUS))
                .build()
        detailsDraweeView.controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(detailsDraweeView.controller)
                .build()
        detailsDraweeViewSmall.setImageURI(url)
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

    private fun initializeView() {

        val layoutManager = LinearLayoutManager(this)
        detailsRecycler.layoutManager = layoutManager
        detailsRecycler.adapter = detailsAdapter
        val dividerItemDecoration = DividerItemDecoration(this,
                layoutManager.orientation)
        detailsRecycler.addItemDecoration(dividerItemDecoration)
        detailsDownloadButton.setOnClickListener({
            presenter.onDownloadClicked()
        })
        detailsProgress.sty
    }




}