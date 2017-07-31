package com.josebigio.mediadownloader.models

/**
 * Created by josebigio on 7/30/17.
 */
class SearchResponse(
        val results: List<SearchResult>
)

class SearchResult(
        val title: String,
        val channelTitle: String,
        val videoId: String,
        val thumbnails: Thumbnail
)

class Thumbnail(
        val default: ThumbnailItem,
        val medium: ThumbnailItem,
        val hight: ThumbnailItem
)

class ThumbnailItem(
        val url: String,
        val width: Int,
        val height: Int
)