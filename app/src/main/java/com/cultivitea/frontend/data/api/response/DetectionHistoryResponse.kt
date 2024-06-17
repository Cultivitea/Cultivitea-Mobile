package com.cultivitea.frontend.data.api.response

import com.google.gson.annotations.SerializedName

data class DetectionHistoryResponse(

	@field:SerializedName("data")
	val data: List<HistoryItem>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class HistoryItem(

	@field:SerializedName("history")
	val history: HistoryDetail? = null
)

data class HistoryDetail(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("teaPlantId")
	val teaPlantId: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("suggestion")
	val suggestion: String? = null
)
