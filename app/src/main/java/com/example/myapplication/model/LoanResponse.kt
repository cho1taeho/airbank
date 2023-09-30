package com.example.myapplication.model

data class LoanResponse (
    val code: String,
    val message: String,
    val data: Data
){

    data class Data(
        val amount: Long,
        val loanLimit: Long
    )
}