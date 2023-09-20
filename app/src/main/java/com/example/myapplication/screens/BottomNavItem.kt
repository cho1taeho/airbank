package com.example.myapplication.screens

import com.example.myapplication.R


sealed class BottomNavItem(
    val title: String, val icon: Int, val screenRoute: String
) {
    object Main : BottomNavItem("홈", R.drawable.ihome, "main")
    object Wallet : BottomNavItem("지갑", R.drawable.iwallet, "wallet")
    object Savings : BottomNavItem("티끌모으기", R.drawable.isavings, "savings")
    object Loan : BottomNavItem("땡겨쓰기", R.drawable.iloan, "loan")
    object Mypage : BottomNavItem("마이페이지", R.drawable.imypage, "mypage")

}