package com.cultivitea.frontend.data.api.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("data")
    val data: PredictionResult?
)

data class PredictionResult (
    @field:SerializedName("teaPlantId")
    val teaPlantId: String,
    @field:SerializedName("imageUrl")
    val imageUrl: String,
    @field:SerializedName("result")
    val result: String,
    @field:SerializedName("suggestion")
    val suggestion: String,
    @field:SerializedName("createdAt")
    val createdAt: String,
)