package com.example.myapplication.model


data class AccountRegisterRequest(
    val bankCode: String,
    val accountNumber: Int
)

data class AccountRegisterResponse(
    val code: String,
    val message: String,
    val data: Data
){
    data class Data(
        val id: Int
    )
}

data class AccountCheckResponse(
    val code: String,
    val message: String,
    val data: Data
) {
    data class Data(
        val accountHistory: List<Transaction>
    ) {
        data class Transaction(
            val id: Int,
            val amount: Int,
            val apiCreatedAt: String,
            val transactionType: String,
            val transactionDistinction: String,
            val transactionPartner: String
        )
    }
}