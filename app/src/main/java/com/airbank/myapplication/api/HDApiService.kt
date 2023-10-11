package com.airbank.myapplication.api

import com.airbank.myapplication.model.GETCreditHistoryResponse
import com.airbank.myapplication.model.GETGroupsEnrollResponse
import com.airbank.myapplication.model.GETGroupsResponse
import com.airbank.myapplication.model.GETLogoutResponse
import com.airbank.myapplication.model.GETMembersResponse
import com.airbank.myapplication.model.PATCHGroupsConfirmRequest
import com.airbank.myapplication.model.PATCHGroupsConfirmResponse
import com.airbank.myapplication.model.PATCHGroupsFundResponse
import com.airbank.myapplication.model.PATCHMembersRequest
import com.airbank.myapplication.model.PATCHMembersResponse
import com.airbank.myapplication.model.POSTGroupsFundRequest
import com.airbank.myapplication.model.POSTGroupsFundResponse
import com.airbank.myapplication.model.POSTGroupsRequest
import com.airbank.myapplication.model.POSTGroupsResponse
import com.airbank.myapplication.model.POSTLoginRequest
import com.airbank.myapplication.model.POSTLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query


interface HDApiService {

    @GET("/auth/logout")
    suspend fun logoutUser(): Response<GETLogoutResponse>
    @POST("/auth/login")
    suspend fun loginUser(@Body postLoginRequest: POSTLoginRequest): Response<POSTLoginResponse>

    @PATCH("/members")
    suspend fun signupUser(@Body patchMembersRequest: PATCHMembersRequest): Response<PATCHMembersResponse>
    @GET("/members")
    suspend fun getUserInfo(): Response<GETMembersResponse>

    @GET("/groups")
    suspend fun getGroups() : Response<GETGroupsResponse>
    @POST("/groups")
    suspend fun postGroups(@Body postGroupsRequest: POSTGroupsRequest) :Response<POSTGroupsResponse>
    @PATCH("/groups/confirm")
    suspend fun patchGroupsConfirm(@Body patchGroupsConfirmRequest: PATCHGroupsConfirmRequest, @Query("group_id") groupId: Int): Response<PATCHGroupsConfirmResponse>
    @POST("/groups/fund")
    suspend fun postGroupsFund(@Body postGroupsFundRequest: POSTGroupsFundRequest, @Query("group_id") groupId: Int): Response<POSTGroupsFundResponse>
    @PATCH("/groups/fund")
    suspend fun patchGroupsFund(@Body patchGroupsFundRequest: POSTGroupsFundRequest, @Query("group_id") groupId: Int): Response<PATCHGroupsFundResponse>

    @GET("/groups/fund")
    suspend fun getGroupsFund(@Query("group_id") groupId: Int): Response<PATCHGroupsFundResponse>

    @GET("/groups/enroll")
    suspend fun getGroupsEnroll():Response<GETGroupsEnrollResponse>

    @GET("/members/credit-history")
    suspend fun getCreditHistory(@Query("group_id") groupId: Int):Response<GETCreditHistoryResponse>


}








