package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.myapplication.R

@Composable
fun FirstScreen(navController: NavController){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter =
            painterResource(
                id = R.drawable.airbank),
            contentDescription = "LOGO",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        )
        Button(onClick = { navController.navigate(BottomNavItem.SignIn.screenRoute) }) {
            Image(painter = painterResource(id = R.drawable.kakao_login_large_narrow), contentDescription ="kakao" )
        }
    }

}