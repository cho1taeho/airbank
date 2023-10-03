package com.example.myapplication.api

import com.example.myapplication.model.GETGroupsResponse
import com.example.myapplication.model.GETLogoutResponse
import com.example.myapplication.model.POSTLoginRequest
import com.example.myapplication.model.POSTLoginResponse
import com.example.myapplication.model.SavingsResponse
import com.example.myapplication.model.PATCHMembersRequest
import com.example.myapplication.model.PATCHMembersResponse
import com.example.myapplication.model.GETMembersResponse
import com.example.myapplication.model.PATCHGroupsConfirmRequest
import com.example.myapplication.model.PATCHGroupsConfirmResponse
import com.example.myapplication.model.PATCHGroupsFundRequest
import com.example.myapplication.model.PATCHGroupsFundResponse
import com.example.myapplication.model.POSTGroupsFundRequest
import com.example.myapplication.model.POSTGroupsFundResponse
import com.example.myapplication.model.POSTGroupsRequest
import com.example.myapplication.model.POSTGroupsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path


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
    @PATCH("/groups/confirm?group_id={group_id}")
    suspend fun patchGroupsConfirm(@Body patchGroupsConfirmRequest: PATCHGroupsConfirmRequest, @Path("group_id") groupId: Int): Response<PATCHGroupsConfirmResponse>
    @POST("/groups/fund?group_id={group_id}")
    suspend fun postGroupsFund(@Body postGroupsFundRequest: POSTGroupsFundRequest, @Path("group_id") groupId: Int): Response<POSTGroupsFundResponse>

    @PATCH("/groups/fund?group_id={group_id}")
    suspend fun patchGroupsFund(@Body patchGroupsFundRequest: PATCHGroupsFundRequest, @Path("group_id") groupId: Int): Response<PATCHGroupsFundResponse>

}








