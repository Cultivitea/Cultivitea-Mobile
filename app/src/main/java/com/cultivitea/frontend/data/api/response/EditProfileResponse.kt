package com.cultivitea.frontend.data.api.response

import com.google.gson.annotations.SerializedName

data class EditProfileResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("userCredential")
    val userCredential: UserCredential?,
    @field:SerializedName("updatedProfile")
    val updatedProfile: UpdatedProfile?
)

data class UpdatedProfile (
    @field:SerializedName("username")
    val username: String,
    @field:SerializedName("imageUrl")
    val imageUrl: String,
    @field:SerializedName("phoneNumber")
    val phoneNumber: String,
    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String,
)