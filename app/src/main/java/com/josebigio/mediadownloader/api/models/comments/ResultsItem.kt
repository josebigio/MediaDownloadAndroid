package com.josebigio.mediadownloader.api.models.comments

data class ResultsItem(
	val snippet: Snippet? = null,
	val replies: Replies? = null,
	val kind: String? = null,
	val etag: String? = null,
	val id: String? = null
)
