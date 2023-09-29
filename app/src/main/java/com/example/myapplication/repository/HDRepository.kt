package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.model.LoginRequest
import com.example.myapplication.model.LoginResponse
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import com.example.myapplication.model.SignUpRequest
import com.example.myapplication.model.SignUpResponse
import com.example.myapplication.network.HDRetrofitBuilder
import retrofit2.HttpException


class AuthRepository(private val scope: CoroutineScope) {
    fun retrieveUserInfo(
        token: OAuthToken,
        onComplete: (LoginRequest) -> Unit
    ) {
        Log.d("Token",token.toString())
        scope.launch(Dispatchers.IO) {
            UserApiClient.instance.me { user, userInfoError ->
                if (userInfoError != null) {
                    // Handle user info request failure here
                } else if (user != null) {
                    val oauthIdentifier = user.id.toString()
                    val profileImageUrl = user.properties?.get("profile_image") ?: ""
                    val isDefaultImageString =
                        user.kakaoAccount?.profile?.isDefaultImage?.toString() ?: ""
                    val isDefaultImage = isDefaultImageString.toBoolean()

                    val loginRequest = LoginRequest(
                        oauthIdentifier = oauthIdentifier,
                        imageURL = profileImageUrl,
                        isDefaultImage = isDefaultImage
                    )

                    onComplete(loginRequest)
                }
            }
        }
    }

    fun performLoginRequest(
        loginRequest: LoginRequest,
        onComplete: (Response<LoginResponse>) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                Log.d("POSTTT",loginRequest.toString())
                val response = HDRetrofitBuilder.HDapiService().loginUser(loginRequest)
                Log.d("POSTTT",response.toString())

                onComplete(response)
            } catch (e: Exception) {
                // Handle network request exception
                onComplete(Response.error(500, okhttp3.ResponseBody.create(null, e.message ?: "Unknown error")))
            }
        }
    }

    fun performLogout(onComplete: (Throwable?) -> Unit) {
        scope.launch(Dispatchers.IO) {
            UserApiClient.instance.logout { error ->
                onComplete(error)
            }
        }
    }
}


class SignUpRepository {
    suspend fun signUp(signUpRequest: SignUpRequest): Result<SignUpResponse> {
        return try {
            val response = HDRetrofitBuilder.HDapiService().signupUser(signUpRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
