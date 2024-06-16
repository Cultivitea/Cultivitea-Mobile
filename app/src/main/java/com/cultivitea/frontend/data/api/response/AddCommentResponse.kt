package com.cultivitea.frontend.data.api.response

import com.google.gson.annotations.SerializedName

data class AddCommentResponse(

	@field:SerializedName("data")
	val data: CommentDetail? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CommentDetail(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("creatorUid")
	val creatorUid: String? = null,

	@field:SerializedName("creator")
	val creator: String? = null,

	@field:SerializedName("commentId")
	val commentId: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
