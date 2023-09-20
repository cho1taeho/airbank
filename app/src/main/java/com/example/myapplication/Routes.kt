package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.myapplication.screens.MainScreen
import com.example.myapplication.screens.ChildSavingsApplication
import com.example.myapplication.screens.ChildSavingsScreen
import com.example.myapplication.screens.LoanScreen
import com.example.myapplication.screens.SavingsScreen
import com.example.myapplication.screens.WalletScreen
import androidx.navigation.compose.NavHost
import com.example.myapplication.screens.BottomNavItem

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = BottomNavItem.Main.screenRoute) {
        composable(BottomNavItem.Main.screenRoute) {
            MainScreen(navController = navController)
        }
        composable(BottomNavItem.Savings.screenRoute) {
            SavingsScreen(navController = navController)
        }
        composable(BottomNavItem.Loan.screenRoute) {
            LoanScreen(navController = navController)
        }
        composable(BottomNavItem.Wallet.screenRoute) {
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

