package com.josebigio.mediadownloader.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josebigio.mediadownloader.R
import kotlinx.android.synthetic.main.search_cell.view.*

/**
 * Created by josebigio on 7/31/17.
 */
class SearchAdapter(val delegate:SearchAdapterDelegate) : RecyclerView.Adapter<SearchViewHolder>() {

    var dataSet = ArrayList<SearchItem>()

    override fun onBindViewHolder(holder: SearchViewHolder?, position: Int) {
        holder!!.bind((dataSet[position]), View.OnClickListener { delegate.onItemClick(dataSet[position]) })
    }

    override fun getItemCount(): Int {
       return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchViewHolder {
       return SearchViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.search_cell,parent,false))
    }

}

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(searchItem: SearchItem, onClickListener: View.OnClickListener) {
        itemView.searchTextView.text = searchItem.title
        itemView.searchThumbnailImage.setImageURI(searchItem.url)
        itemView.setOnClickListener(onClickListener)
    }
}

data class SearchItem(val title: String, val url: String)

interface SearchAdapterDelegate {
    fun onItemClick(searchItem: SearchItem)
}