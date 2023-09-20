package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.MainScreen
import com.example.myapplication.screens.ChildSavingsApplication
import com.example.myapplication.screens.ChildSavingsScreen
import com.example.myapplication.screens.LoanScreen
import com.example.myapplication.screens.SavingsScreen
import com.example.myapplication.screens.WalletScreen
import androidx.navigation.compose.NavHost

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController)
        }
        composable("savings") {
            SavingsScreen(navController = navController)
        }
        composable("loan") {
            LoanScreen(navController = navController)
        }
        composable("wallet") {
            WalletScreen(navController = navController)
        }
        composable("savingsApplication") {
            ChildSavingsApplication(navController = navController)
        }
        composable("childSavings") {
            ChildSavingsScreen(navController = navController)
        }
    }
}

