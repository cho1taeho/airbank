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
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException

class AuthRepository(private val scope: CoroutineScope) {
    fun retrieveUserInfo( //카카오sdk활용, user.instance(이름,프로필이미지,기본프로필여부)를 반환
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

    fun performLoginRequest( //반환받은 유저 정보를 서버에 보냄
        loginRequest: LoginRequest,
        onComplete: (Response<LoginResponse>) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                Log.d("POSTTT",loginRequest.toString())
                val response = HDRetrofitBuilder.HDapiService().loginUser(loginRequest) //retrofit2활용, airbank서버에 유저 정보 1차 전송
                Log.d("POSTTT",response.toString())

                onComplete(response)
            } catch (e: Exception) {
                // Handle network request exception
                onComplete(Response.error(500, (e.message ?: "Unknown error").toResponseBody(null)))
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


class SignUpRepository(private val scope: CoroutineScope) {
    val TAG = "SIGNUP"
    fun signUp(
        signUpRequest: SignUpRequest,
        onComplete: (Response<SignUpResponse>) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
             try {
                 Log.d(TAG,signUpRequest.toString())
                val response = HDRetrofitBuilder.HDapiService().signupUser(signUpRequest)
                 Log.d(TAG,response.toString())

                    onComplete(response)
            } catch (e: Exception) {
                 onComplete(Response.error(500, (e.message ?: "Unknown error").toResponseBody(null)))
            }
        }
    }
}
