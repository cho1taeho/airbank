package com.example.myapplication.screens

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@Composable
fun NaverScreen(navController: NavController) {
//    val url = "https://m.naver.com" // Your desired URL
    val url = "https://airbank.ssafy.life/auth/login"
    AndroidView(
        factory = { context ->
            WebView(context).apply{
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}