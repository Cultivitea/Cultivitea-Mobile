package com.cultivitea.frontend.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DiscussionResponse(

	@field:SerializedName("data")
	val data: List<DiscussionItem>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class DiscussionItem(
	@SerializedName("createdAt")
	val createdAt: String? = null,

	@SerializedName("creatorUid")
	val creatorUid: String? = null,

	@SerializedName("creator")
	val creator: String? = null,

	@SerializedName("discussionId")
	val discussionId: String? = null,

	@SerializedName("title")
	val title: String? = null,

	@SerializedName("content")
	val content: String? = null
) : Parcelable