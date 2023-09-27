package com.example.myapplication.api

import com.example.myapplication.model.AuthResponse
import com.example.myapplication.model.LoginRequest
import com.example.myapplication.model.SavingsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface HDApiService {
//    @POST("/auth/login")
//    suspend fun loginUser(@Body LoginRequest: LoginRequest): String


    @GET("/auth/logout")
    suspend fun logoutUser(): Response<SavingsResponse>
    @POST("/auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<AuthResponse>

}








