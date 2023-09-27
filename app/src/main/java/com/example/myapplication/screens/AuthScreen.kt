package com.example.myapplication.screens

import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.myapplication.model.AuthResponse
import com.example.myapplication.model.LoginRequest
import com.example.myapplication.network.HDRetrofitBuilder.HDapiService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AuthScreen(navController: NavController) {
    val TAG = "KAKAO"

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                setLoginClickListener(navController)
            }
        }
    )
    Button(
        onClick = { performLogout(navController) },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "로그아웃")
    }
}

fun WebView.setLoginClickListener(navController: NavController) {
    val TAG = "KAKAO"

    fun handleLogin(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            Log.e(TAG, "로그인 실패", error)
            // Handle login failure here
        } else if (token != null) {
            Log.i(TAG, "로그인 성공 ${token.accessToken}")
            // After successful login, request user information
            retrieveUserInfo(token, navController)
        }
    }

    // Check if KakaoTalk login is available
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            handleLogin(token, error)
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            handleLogin(token, error)
        }
    }
}

fun retrieveUserInfo(token: OAuthToken, navController: NavController) {
    val TAG = "KAKAO LOGIN"

    UserApiClient.instance.me { user, userInfoError ->
        if (userInfoError != null) {
            Log.e(TAG, "사용자 정보 요청 실패", userInfoError)
            // Handle user info request failure here
        } else if (user != null) {
            val oauthIdentifier = user.id.toString()
            val profileImageUrl = user.properties?.get("profile_image") ?: ""
            val isDefaultImageString = user.kakaoAccount?.profile?.isDefaultImage?.toString() ?: ""

            // Convert the isDefaultImageString to a Boolean
            val isDefaultImage = isDefaultImageString.toBoolean()

            // Create a LoginRequest object with extracted information
            val loginRequest = LoginRequest(
                oauthIdentifier = oauthIdentifier,
                imageURL = profileImageUrl,
                isDefaultImage = isDefaultImage
            )

            // Make a POST request to your API
            performLoginRequest(loginRequest, navController)
        }
    }
}

fun performLoginRequest(loginRequest: LoginRequest, navController: NavController) {
    val TAG = "KAKAO LOGIN"

    HDapiService.loginUser(loginRequest).enqueue(object : Callback<AuthResponse> {
        override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null && (loginResponse.data.name.isNullOrEmpty() || loginResponse.data.phoneNumber.isNullOrEmpty())) {
                    // Either name or phoneNumber is empty, navigate to the signup page
                    Log.d(TAG, "name "+ loginResponse.data.name)
                    Log.d(TAG, "number "+ loginResponse.data.phoneNumber)

                    navController.navigate(BottomNavItem.Wallet.screenRoute)
                } else {
                    // Both name and phoneNumber are not empty, navigate to the main screen
                    navController.navigate(BottomNavItem.Main.screenRoute)
                }
            } else {
                // Handle the response status other than success
                Log.e(TAG, "Login failed with HTTP status code: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
            Log.e(TAG, "POST request failed", t)
        }
    })
}

fun performLogout(navController: NavController) {
    val TAG = "KAKAO"

    UserApiClient.instance.logout { error ->
        if (error != null) {
            Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
        } else {
            Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            // Optionally, you can navigate the user to a different screen after logout
            navController.navigate(BottomNavItem.Main.screenRoute)
        }
    }
}
