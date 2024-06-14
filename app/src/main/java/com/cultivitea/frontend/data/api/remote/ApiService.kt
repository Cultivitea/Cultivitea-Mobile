package com.cultivitea.frontend.data.api.remote

import com.cultivitea.frontend.data.api.response.PredictionResponse
import com.cultivitea.frontend.data.api.response.LoginResponse
import com.cultivitea.frontend.data.api.response.SignUpResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("signin")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("signup")
    suspend fun registerUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignUpResponse

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Header("Cookie") token: String,
        @Part image: MultipartBody.Part,
    ): PredictionResponse

    @GET("profile/{id}")
    suspend fun getProfile(
        @Header("Cookie") token: String,
        @Path("id") id: String
    ): LoginResponse
}
