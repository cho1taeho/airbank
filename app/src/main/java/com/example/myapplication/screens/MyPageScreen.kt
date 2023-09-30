package com.example.myapplication.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.AirbankApplication
import com.example.myapplication.network.HDRetrofitBuilder
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MyPageScreen(navController: NavController) {
    Text("HD")
    var imagepath by remember { mutableStateOf("") }
    UserApiClient.instance.me { user, _ ->
        if (user!=null){
            imagepath = user.properties?.get("profile_image") ?: ""
        }
    }
    AsyncImage(
        model = imagepath,
        contentDescription = null,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
    )
    Button(
        onClick = { performLogout(navController) }, // Use the ViewModel for logout
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "로그아웃")
    }
}

fun performLogout(navController: NavController) {
    val TAG = "LOGOUT"
    UserApiClient.instance.logout { error -> Log.e(TAG,error.toString()) }
//    val response = HDRetrofitBuilder.HDapiService().loginUser(loginRequest)
    AirbankApplication.prefs.setString("JSESSIONID","")
    navController.navigate("First")
}
