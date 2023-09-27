package com.example.myapplication.repository

import com.example.myapplication.api.ApiService
import com.example.myapplication.model.BonusSavingsRequest
import com.example.myapplication.model.CancelSavingsRequest
import com.example.myapplication.model.CreateSavingsItemRequest
import com.example.myapplication.model.SavingsRemitRequest
import com.example.myapplication.model.UpdateSavingsRequest
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


class SavingsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getSavings(groupId: Int) = apiService.getSavings(groupId)

    suspend fun createSavingsItem(request: CreateSavingsItemRequest) = apiService.createSavingsItem(request)

    suspend fun updateSavings(groupId: Int, request: UpdateSavingsRequest) = apiService.updateSavings(groupId, request)

    suspend fun cancelSavings(request: CancelSavingsRequest) = apiService.cancelSavings(request)

    suspend fun remitSavings(request: SavingsRemitRequest) = apiService.remitSavings(request)

    suspend fun bonusSavings(groupId: Int, request: BonusSavingsRequest) = apiService.bonusSavings(groupId, request)

}
