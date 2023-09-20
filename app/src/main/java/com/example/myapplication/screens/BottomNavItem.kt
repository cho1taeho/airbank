package com.example.myapplication.screens

import com.example.myapplication.R


sealed class BottomNavItem(
    val title: String, val icon: Int, val screenRoute: String
) {
    companion object {
        fun fromRoute(route: String): BottomNavItem {
            return when (route) {
                "main" -> main
                "savings" -> savings
                "loan" -> loan
                "wallet" -> wallet
                "mypage" -> mypage
                // Add cases for other routes
                else -> main // Default to the main route
            }
        }
    }
    object main : BottomNavItem("홈", R.drawable.ihome, "main")
    object wallet : BottomNavItem("지갑", R.drawable.iwallet, "wallet")
    object savings : BottomNavItem("티끌모으기", R.drawable.isavings, "savings")
    object loan : BottomNavItem("땡겨쓰기", R.drawable.iloan, "loan")
    object mypage : BottomNavItem("마이페이지", R.drawable.imypage, "mypage")

}