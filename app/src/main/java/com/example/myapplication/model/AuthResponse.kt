package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("name")
        val name: String,
        @SerializedName("phoneNumber")
        val phoneNumber: String,
    )
}
data class LoginRequest(
    @SerializedName("oauthIdentifier")
    val oauthIdentifier : String,
    @SerializedName("imageURL")
    val imageURL : String,
    @SerializedName("isDefaultImage")
    val isDefaultImage : Boolean
)

data class SignUpRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("role")
    val role: String
)

data class SignUpResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("name")
        val name: String,
        @SerializedName("phoneNumber")
        val phoneNumber: String,
        @SerializedName("role")
        val role: String
    )
}