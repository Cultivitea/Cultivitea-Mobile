package com.cultivitea.frontend.data.repository

import com.cultivitea.frontend.data.api.pref.UserModel
import com.cultivitea.frontend.data.api.pref.UserPreference
import com.cultivitea.frontend.data.api.remote.ApiService
import com.cultivitea.frontend.data.api.response.AddCommentResponse
import com.cultivitea.frontend.data.api.response.AddDiscussionResponse
import com.cultivitea.frontend.data.api.response.CommentResponse
import com.cultivitea.frontend.data.api.response.DiscussionResponse
import com.cultivitea.frontend.data.api.response.EditProfileResponse
import com.cultivitea.frontend.data.api.response.PredictionResponse
import com.cultivitea.frontend.data.api.response.LoginResponse
import com.cultivitea.frontend.data.api.response.SignUpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

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

    suspend fun getProfile(token: String, id: String): LoginResponse {
        val accessToken = "access_token=" + token
        return apiService.getProfile(accessToken, id)
    }

    suspend fun editProfile(
        token: String,
        id: String,
        name: String,
        phoneNumber: String,
        dateOfBirth: String,
        image: MultipartBody.Part?
    ): EditProfileResponse {
        val accessToken = "access_token=" + token
        if (image == null) {
            return apiService.editProfileWithoutImage(accessToken, id, name, phoneNumber, dateOfBirth)
        }
        val nameRequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneNumberRequestBody = phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())
        val dateOfBirthRequestBody = dateOfBirth.toRequestBody("text/plain".toMediaTypeOrNull())
        return apiService.editProfile(accessToken, id, image, nameRequestBody, phoneNumberRequestBody, dateOfBirthRequestBody)
    }


    suspend fun predict(file: MultipartBody.Part): PredictionResponse {
        val token = "access_token=" + userPreference.getSession().first().token
        return apiService.predict(token, image = file)
    }

    suspend fun register(name: String, email: String, password: String): SignUpResponse {
        return apiService.registerUser(name, email, password)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getDiscussionsComments(id: String): CommentResponse {
        return apiService.getDiscussionsComments(id)
    }

    suspend fun getDiscussions() : DiscussionResponse {
        return apiService.getDiscussions()
    }

    suspend fun addComment(id: String, comment: String) : AddCommentResponse {
        val token = "access_token=" + userPreference.getSession().first().token
        return apiService.addDiscussionComments(token, id, comment)
    }

    suspend fun addDiscussion(title: String, content: String) : AddDiscussionResponse {
        val token = "access_token=" + userPreference.getSession().first().token
        return apiService.addDiscussion(token, title, content)
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