package com.example.myapplication.screens

import com.example.myapplication.R


sealed class BottomNavItem(
    val title: String, val icon: Int, val screenRoute: String, val selectedIcon: Int
) {
    companion object {
        fun fromRoute(route: String): BottomNavItem {
            return when (route) {
                "main" -> Main
                "savings" -> Savings
                "Loan" -> Loan
                "ChildWallet" -> ChildWallet
                "wallet" -> Wallet
                "my" -> MyPage
                "ChildLoan" -> ChildLoan
                "ChildLoanStart" -> ChildLoanStart
                "ChildLoanRepayment" -> ChildLoanRepayment
                "ChildTransactionHistory" -> ChildTransactionHistory
                "childSavings" -> ChildSavings
                "savingsApplication" -> ChildSavingsApplication
                "savingsApprove" -> SavingsApprove
                "savingsBonus" -> SavingsBonus
                "SavingsWaiting" -> SavingsWaiting
                "BonusTransfer" -> BonusTransfer
                "notification" -> Notification
                // Add cases for other routes
                else -> Main // Default to the main route
            }
        }
    }
    object Main : BottomNavItem("메인", R.drawable.ihome, "main",R.drawable.ihome)
    object Wallet : BottomNavItem("지갑", R.drawable.iwallet, "wallet", R.drawable.iwallet)
    object ChildWallet : BottomNavItem("지갑", R.drawable.iwallet, "ChildWallet", R.drawable.iwallet)
    object Savings : BottomNavItem("티끌 모으기", R.drawable.isavings, "savings",R.drawable.isavings)
    object SavingsBonus : BottomNavItem("티끌 모으기", R.drawable.isavings, "savingsBonus",R.drawable.isavings)
    object SavingsWaiting : BottomNavItem("티끌 모으기", R.drawable.isavings, "SavingsWaiting",R.drawable.isavings)
    object BonusTransfer : BottomNavItem("보너스", R.drawable.isavings, "BonusTransfer",R.drawable.isavings)

    object ChildSavings : BottomNavItem("티끌 모으기", R.drawable.isavings, "childSavings",R.drawable.isavings)
    object ChildSavingsApplication : BottomNavItem("티끌 모으기", R.drawable.isavings, "savingsApplication",R.drawable.isavings)
    object SavingsApprove : BottomNavItem("티끌 모으기", R.drawable.isavings, "savingsApprove",R.drawable.isavings)
    object Loan : BottomNavItem("땡겨쓰기", R.drawable.iloan, "Loan",R.drawable.iloan)
    object ChildLoan : BottomNavItem("땡겨쓰기", R.drawable.iloan, "ChildLoan",R.drawable.iloan)
    object ChildLoanStart : BottomNavItem("떙겨쓰기", R.drawable.iloan, "ChildLoanStart",R.drawable.iloan)
    object ChildLoanRepayment : BottomNavItem("떙겨쓰기", R.drawable.iloan, "ChildLoanRepayment",R.drawable.iloan)
    object MyPage : BottomNavItem("마이 페이지", R.drawable.imypage, "my",R.drawable.imypage)
    object Notification :BottomNavItem("알림", androidx.core.R.drawable.notification_bg,"notification",androidx.core.R.drawable.notification_bg)
    object SignUp : BottomNavItem("회원가입",0,"SignUp",0)
    object SignIn : BottomNavItem("로그인",0,"SignIn",0)
    object ChildTransactionHistory : BottomNavItem("거래 내역", 0, "ChildTransactionHistory",0)


}