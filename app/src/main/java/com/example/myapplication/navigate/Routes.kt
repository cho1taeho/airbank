package com.example.myapplication.navigate

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.AirbankApplication
import com.example.myapplication.screens.AuthScreen
import com.example.myapplication.screens.BottomNavItem
import com.example.myapplication.screens.ChildMainScreen
import com.example.myapplication.screens.ChildSavingsApplication
import com.example.myapplication.screens.ChildSavingsScreen
import com.example.myapplication.screens.ChildSavingsTransferScreen
import com.example.myapplication.screens.FirstScreen
import com.example.myapplication.screens.LoanScreen
import com.example.myapplication.screens.MainScreen
import com.example.myapplication.screens.MyPageScreen
import com.example.myapplication.screens.NotificationScreen
import com.example.myapplication.screens.SavingsApproveScreen
import com.example.myapplication.screens.SavingsBonusScreen
import com.example.myapplication.screens.SavingsScreen
import com.example.myapplication.screens.SignInScreen
import com.example.myapplication.screens.SignUpScreen
import com.example.myapplication.screens.WalletScreen
import com.example.myapplication.viewmodel.SavingsViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AppNavigation(navController: NavHostController){
    var userRole by remember { mutableStateOf("") }
    userRole = AirbankApplication.prefs.getString("role","")
    NavHost(navController = navController, startDestination = "First") {
        composable(BottomNavItem.Main.screenRoute) {
            Log.d("userRole",userRole)
            if (userRole == "CHILD"){
                ChildMainScreen(navController = navController)
            }
            else{
                MainScreen(navController = navController)
            }
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
        composable(BottomNavItem.MyPage.screenRoute){
            MyPageScreen(navController = navController)
        }
        composable(BottomNavItem.Notification.screenRoute){
            NotificationScreen(navController = navController)
        }

        composable(BottomNavItem.SignUp.screenRoute){
            SignUpScreen(navController = navController)
        }

        composable(BottomNavItem.SignIn.screenRoute){
//            SignInScreen(navController = navController)
            AuthScreen(navController = navController)
        }

        composable("savingsApplication") {
//            val viewModel: SavingsViewModel = viewModel()
            ChildSavingsApplication(navController = navController)
        }
        composable("childSavings") {
            ChildSavingsScreen(navController = navController)
        }
        composable("First"){
            FirstScreen(navController = navController)
        }

        composable("savingsApprove") {
            SavingsApproveScreen(navController = navController)
        }
        composable("savingsTransfer") {
            ChildSavingsTransferScreen(navController = navController)
        }
        composable("savingsBonus"){
            SavingsBonusScreen(navController = navController)
        }

    }
}

