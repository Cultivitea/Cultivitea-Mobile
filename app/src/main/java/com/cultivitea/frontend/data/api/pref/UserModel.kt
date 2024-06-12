package com.cultivitea.frontend.data.api.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)