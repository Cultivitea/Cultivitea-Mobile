package com.cultivitea.frontend.data.api.pref

data class UserModel(
    val token: String,
    val uid: String,
    val phoneNumber: String,
    val imageUrl: String,
    val dateOfBirth: String,
    val email: String,
    val username: String,
    val isLogin: Boolean = false
)

