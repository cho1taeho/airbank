package com.example.myapplication.network

import com.example.myapplication.api.HDApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HDRetrofitBuilder {
    private const val BASE_URL = "https://airbank.ssafy.life/"

    val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val HDapiService: HDApiService = retrofit.create(HDApiService::class.java)
}