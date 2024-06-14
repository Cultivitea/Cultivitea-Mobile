package com.cultivitea.frontend.data.api.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("details")
    val details: Map<String, String>? = null,

    @field:SerializedName("userCredential")
    val userCredential: UserCredentialTemp? = null
)

data class UserCredentialTemp(
    @field:SerializedName("uid")
    val uid: String? = null,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)
