package com.josebigio.mediadownloader.models

/**
 * Created by josebigio on 7/30/17.
 */
data class SearchResponse(
        val results: List<SearchResult>
)

data class SearchResult(
        val title: String,
        val channelTitle: String,
        val videoId: String,
        val thumbnails: Thumbnail
)

data class Thumbnail(
        val default: ThumbnailItem,
        val medium: ThumbnailItem,
        val high: ThumbnailItem
)

data class ThumbnailItem(
        val url: String,
        val width: Int,
        val height: Int
)