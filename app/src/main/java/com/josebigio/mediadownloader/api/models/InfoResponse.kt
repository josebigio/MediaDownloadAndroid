package com.josebigio.mediadownloader.api.models

/**
 * Created by josebigio on 8/4/17.
 */
class InfoResponse(
        val results: InfoResult
)

class InfoResult(
        val publication: String,
        val title: String,
        val description: String,
        val duration: String,
        val viewCount: String,
        val likeCount: String,
        val dislikeCount: String,
        val favoriteCount: String,
        val commentCount: String,
        val thumbnails: Thumbnail
)

class Thumbnail(
        val default: ThumbnailItem,
        val medium: ThumbnailItem,
        val high: ThumbnailItem
)

class ThumbnailItem(
        val url: String,
        val width: Int,
        val height: Int
)