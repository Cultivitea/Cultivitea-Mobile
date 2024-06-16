package com.cultivitea.frontend.data.api.response

import com.google.gson.annotations.SerializedName

data class AddDiscussionResponse(

	@field:SerializedName("data")
	val data: DiscussionDetail? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DiscussionDetail(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("creatorUid")
	val creatorUid: String? = null,

	@field:SerializedName("creator")
	val creator: String? = null,

	@field:SerializedName("discussionId")
	val discussionId: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
