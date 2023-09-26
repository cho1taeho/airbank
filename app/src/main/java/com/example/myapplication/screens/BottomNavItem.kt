package com.example.myapplication.screens

import com.example.myapplication.R
import dagger.hilt.android.HiltAndroidApp


sealed class BottomNavItem(
    val title: String, val icon: Int, val screenRoute: String
) {
    companion object {
        fun fromRoute(route: String): BottomNavItem {
            return when (route) {
                "main" -> Main
                "savings" -> Savings
                "loan" -> Loan
                "wallet" -> Wallet
                "my" -> MyPage

                // Add cases for other routes
                else -> Main // Default to the main route
            }
        }
    }
    object Main : BottomNavItem("", R.drawable.ihome, "main")
    object Wallet : BottomNavItem("지갑", R.drawable.iwallet, "wallet")
    object Savings : BottomNavItem("티끌 모으기", R.drawable.isavings, "savings")
    object Loan : BottomNavItem("땡겨 쓰기", R.drawable.iloan, "loan")
    object MyPage : BottomNavItem("마이 페이지", R.drawable.imypage, "my")
    object Notification :BottomNavItem("알림", androidx.core.R.drawable.notification_bg,"notification")
    object SignUp : BottomNavItem("회원가입",0,"SignUp")
    object SignIn : BottomNavItem("로그인",0,"SignIn")

}