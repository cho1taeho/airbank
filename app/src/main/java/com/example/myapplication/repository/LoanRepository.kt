package com.example.myapplication.repository

import com.example.myapplication.api.ApiService
import javax.inject.Inject

class LoanRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getLoan(groupId: Int) = apiService.getLoan(groupId)
}