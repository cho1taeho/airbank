package com.example.myapplication.api

import com.example.myapplication.model.LoginRequest
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.model.SavingsResponse
import com.example.myapplication.model.SignUpRequest
import com.example.myapplication.model.SignUpResponse
import com.example.myapplication.model.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST


interface HDApiService {

    @GET("/auth/logout")
    suspend fun logoutUser(): Response<SavingsResponse>
    @POST("/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @PATCH("/members")
    suspend fun signupUser(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>
    @GET("/members")
    suspend fun getUserInfo(): Response<UserInfoResponse>

}








