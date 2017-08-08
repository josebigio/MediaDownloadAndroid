package com.josebigio.mediadownloader.api.models.comments

data class Snippet(
	val authorProfileImageUrl: String? = null,
	val textDisplay: String? = null,
	val publishedAt: String? = null,
	val authorChannelId: AuthorChannelId? = null,
	val videoId: String? = null,
	val likeCount: Int? = null,
	val textOriginal: String? = null,
	val authorDisplayName: String? = null,
	val parentId: String? = null,
	val canRate: Boolean? = null,
	val authorChannelUrl: String? = null,
	val viewerRating: String? = null,
	val updatedAt: String? = null,
	val topLevelComment: TopLevelComment? = null
)
