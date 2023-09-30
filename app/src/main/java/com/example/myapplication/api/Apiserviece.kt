package com.example.myapplication.api

import com.example.myapplication.model.BonusSavingsRequest
import com.example.myapplication.model.BounusSavingsResponse
import com.example.myapplication.model.CancelSavingsRequest
import com.example.myapplication.model.CancelSavingsResponse
import com.example.myapplication.model.CreateSavingsItemRequest
import com.example.myapplication.model.CreateSavingsItemResponse
import com.example.myapplication.model.LoanResponse
import com.example.myapplication.model.SavingsRemitRequest
import com.example.myapplication.model.SavingsRemitResponse
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
    @PATCH("/savings/cancel")
    suspend fun cancelSavings(@Body request: CancelSavingsRequest) : Response<CancelSavingsResponse>

    @POST("/savings")
    suspend fun remitSavings(@Body request : SavingsRemitRequest) : Response<SavingsRemitResponse>

    @POST("/savings/reward")
    suspend fun bonusSavings(@Query("group_id") groupId: Int,@Body request: BonusSavingsRequest) : Response<BounusSavingsResponse>

    @GET("/loans")
    suspend fun getLoan(@Query("group_id") groupId: Int): Response<LoanResponse>
}








