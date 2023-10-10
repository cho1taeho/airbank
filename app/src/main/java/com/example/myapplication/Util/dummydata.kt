package com.example.myapplication.Util

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myapplication.model.GETCreditHistoryResponse
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

data class CreditHistory(
    val creditScore: Int,
    val createdAt: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun createRandomCreditHistory():  GETCreditHistoryResponse.Data.creditHistory {

    val randomCreditScore = (500..900).random()

    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

    // 1년 전부터 현재까지의 랜덤한 날짜 생성
    val oneYearMillis: Long = 365 * 24 * 60 * 60 * 1000L /12
    val randomMillis = Random.nextLong(0, oneYearMillis)
    val randomDateTime = currentDateTime.minusNanos(randomMillis * 1_000_000) // 밀리초를 날짜로 변환

    val randomDate = randomDateTime.format(formatter)
    return GETCreditHistoryResponse.Data.creditHistory(randomCreditScore, randomDate)
}

@RequiresApi(Build.VERSION_CODES.O)
val creditHistories: List<GETCreditHistoryResponse.Data.creditHistory> = List(10) {
    createRandomCreditHistory()
}
@RequiresApi(Build.VERSION_CODES.O)
val sortedCreditHistories = creditHistories.sortedBy { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").parse(it.createdAt) }