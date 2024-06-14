package com.cultivitea.frontend.data.repository

import com.cultivitea.frontend.data.api.pref.UserModel
import com.cultivitea.frontend.data.api.pref.UserPreference
import com.cultivitea.frontend.data.api.remote.ApiService
import com.cultivitea.frontend.data.api.response.PredictionResponse
import com.cultivitea.frontend.data.api.response.LoginResponse
import com.cultivitea.frontend.data.api.response.SignUpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody

class Repository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun loginUser(email: String, password: String): LoginResponse {
        return apiService.loginUser(email, password)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun predict(file: MultipartBody.Part): PredictionResponse {
        val token = "access_token=" + userPreference.getSession().first().token
        return apiService.predict(token, image = file)
    }

    suspend fun register(name: String, email: String, password: String): SignUpResponse {
        return apiService.registerUser(name, email, password)
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