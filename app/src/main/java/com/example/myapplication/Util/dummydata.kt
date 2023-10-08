package com.example.myapplication.Util

import com.example.myapplication.model.GETCreditHistoryResponse
import java.text.SimpleDateFormat
import java.util.Date

data class CreditHistory(
    val creditScore: Int,
    val createdAt: String
)

fun createRandomCreditHistory():  GETCreditHistoryResponse.Data.creditHistory {
    val randomCreditScore = (500..900).random()
    val currentDateTime = Date().time
    val oneYearInMillis = 365 * 24 * 60 * 60 * 1000 // 1년의 밀리초
    val randomDateBuffer = (currentDateTime - oneYearInMillis..currentDateTime).random()
    val randomDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").format(randomDateBuffer)
    return GETCreditHistoryResponse.Data.creditHistory(randomCreditScore, randomDate)
}

val creditHistories: List<GETCreditHistoryResponse.Data.creditHistory> = List(10) {
    createRandomCreditHistory()
}
val sortedCreditHistories = creditHistories.sortedBy { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").parse(it.createdAt) }