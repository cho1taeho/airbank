package com.example.myapplication.repository

import com.example.myapplication.api.ApiService
import com.example.myapplication.model.LoanResponse
import com.example.myapplication.model.Resource
import com.example.myapplication.model.State
import retrofit2.Response
import javax.inject.Inject

class LoanRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getLoan(groupId: Int): Resource<LoanResponse> {
        val response = apiService.getLoan(groupId)
        return if (response.isSuccessful) {
            Resource(State.SUCCESS, response.body(), "SUCCESS")
        } else {
            Resource(State.ERROR, null, "ERROR")
        }
    }
}