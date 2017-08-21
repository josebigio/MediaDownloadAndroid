package com.josebigio.mediadownloader.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.models.MediaFile
import kotlinx.android.synthetic.main.library_cell.view.*

/**
 * Created by josebigio on 8/18/17.
 */
class LibraryAdapter(val delegate: LibraryAdapterDelegate): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface LibraryAdapterDelegate {
        fun onItemClicked(file: MediaFile)
    }

    var dataSet: List<MediaFile> = ArrayList()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is LibraryItemViewHolder) {
            holder.bind(dataSet[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LibraryItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.library_cell,parent,false))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private inner class LibraryItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(mediaFile: MediaFile) {
            itemView.libraryCellTextView.text = mediaFile.fileName?:mediaFile.fileId
            itemView.libraryCellDraweeView.setImageURI(mediaFile.thumnailUrl)
            itemView.setOnClickListener { delegate.onItemClicked(mediaFile) }
        }
    }

}