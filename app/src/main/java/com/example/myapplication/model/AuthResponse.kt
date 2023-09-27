package com.example.myapplication.model

data class AuthResponse(
    val code: String,
    val message: String,
    val data: Data
) {
    data class Data(
        val name: String,
        val phoneNumber: Int,
    )
}

data class LoginRequest(
    val oauthIdentifier : String,
    val imageURL : String,
    val isDefaultImage : Boolean
)

