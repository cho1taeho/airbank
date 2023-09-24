package com.example.myapplication.api

import com.example.myapplication.model.CreateSavingsItemRequest
import com.example.myapplication.model.CreateSavingsItemResponse
import com.example.myapplication.model.SavingsResponse
import com.example.myapplication.model.UpdateSavingsRequest
import com.example.myapplication.model.UpdateSavingsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @GET("/savings/current")
    suspend fun getSavings(@Query("group_id") groupId: Int): Response<SavingsResponse>

    @POST("/savings/item")
    suspend fun createSavingsItem(@Body request: CreateSavingsItemRequest): Response<CreateSavingsItemResponse>
    @PATCH("/savings/confirm")
    suspend fun updateSavings(@Query("group_id") groupId: Int, @Body request: UpdateSavingsRequest): Response<UpdateSavingsResponse>
}








