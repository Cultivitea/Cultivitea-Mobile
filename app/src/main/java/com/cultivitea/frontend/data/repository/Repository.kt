package com.cultivitea.frontend.data.repository

import com.cultivitea.frontend.data.api.pref.UserModel
import com.cultivitea.frontend.data.api.pref.UserPreference
import com.cultivitea.frontend.data.api.remote.ApiService
import com.cultivitea.frontend.data.api.response.PredictionResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class Repository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun predict(file: MultipartBody.Part): PredictionResponse {
        return apiService.predict(image = file)
    }


    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, userPreference)
            }.also { instance = it }
    }
}