package com.example.myapplication.network

import com.example.myapplication.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

    object RetrofitBuilder {
        private const val BASE_URL = "https://airbank.ssafy.life/"
        fun createApiService(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }