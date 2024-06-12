package com.cultivitea.frontend.data.api.remote

import com.cultivitea.frontend.data.api.response.PredictionResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part image: MultipartBody.Part,
    ): PredictionResponse
}
