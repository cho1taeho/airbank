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
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

@Composable
fun AuthScreen(navController: NavController){
    val TAG : String = "KAKAO"


    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                val TAG: String = "KAKAO"

                // Define a function to handle user information retrieval
                fun handleUserInfo(user: User?, error: Throwable?) {

                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                        // Handle user info request failure here
                    } else if (user != null) {
                        Log.i(TAG, "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                        Log.i(TAG,user.toString())
                        // Handle user info request success here
                    }
                }

                // Define a function to handle login
                fun handleLogin(token: OAuthToken?, error: Throwable?) {
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error)
                        // Handle login failure here
                    } else if (token != null) {
                        Log.i(TAG, "로그인 성공 ${token.accessToken}")
                        // After successful login, request user information
                        UserApiClient.instance.me { user, userInfoError ->
                            handleUserInfo(user, userInfoError)
                        }
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
        }
    )
    Button(
        onClick = {
            // Perform logout
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                } else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    // Optionally, you can navigate the user to a different screen after logout
                    navController.navigate(BottomNavItem.Main.screenRoute)
                }
            }
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "로그아웃")
    }
}