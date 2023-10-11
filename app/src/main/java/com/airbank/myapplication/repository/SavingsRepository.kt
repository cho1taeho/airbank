package com.airbank.myapplication.repository

import android.util.Log
import com.airbank.myapplication.api.ApiService
import com.airbank.myapplication.model.BonusSavingsRequest
import com.airbank.myapplication.model.BonusSavingsResponse
import com.airbank.myapplication.model.CancelSavingsRequest
import com.airbank.myapplication.model.CancelSavingsResponse
import com.airbank.myapplication.model.CreateSavingsItemRequest
import com.airbank.myapplication.model.CreateSavingsItemResponse
import com.airbank.myapplication.model.NotificationResponse
import com.airbank.myapplication.model.Resource
import com.airbank.myapplication.model.SavingsRemitRequest
import com.airbank.myapplication.model.SavingsRemitResponse
import com.airbank.myapplication.model.SavingsResponse
import com.airbank.myapplication.model.State
import com.airbank.myapplication.model.UpdateSavingsRequest
import com.airbank.myapplication.model.UpdateSavingsResponse
import dagger.hilt.android.HiltAndroidApp
import okhttp3.MultipartBody
import javax.inject.Inject


class SavingsRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getSavings(groupId: Int): Resource<SavingsResponse> {
        val response = apiService.getSavings(groupId)
        return if (response.isSuccessful) {
            Resource(State.SUCCESS, response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR, null, "ERROR")
        }
    }

    suspend fun createSavingsItem(request: CreateSavingsItemRequest): Resource<CreateSavingsItemResponse> {
        val response = apiService.createSavingsItem(request)
        Log.d("CreateItem", "티클 ${response.code()}")
        Log.d("CreateItem", "티클 ${response.message()}")
        return if (response.isSuccessful) {
            Resource(State.SUCCESS, response.body(), "SUCCESS !")
        } else {
            Resource(State.ERROR, null, "ERROR !")
        }
    }

    suspend fun updateSavings(groupId: Int, request: UpdateSavingsRequest): Resource<UpdateSavingsResponse> {
        val response = apiService.updateSavings(groupId, request)
        return if (response.isSuccessful) {
            Resource(State.SUCCESS, response.body(), "SUCCESS !")
        } else {
            Resource(State.ERROR, null, "ERROR !")
        }
    }

    suspend fun cancelSavings(request: CancelSavingsRequest): Resource<CancelSavingsResponse> {
        val response = apiService.cancelSavings(request)
        Log.d("CancelItem", "티끌캔슬c ${response.code()}")
        Log.d("CancelItem", "티끌캔슬m ${response.message()}")
        return if (response.isSuccessful) {
            Resource(State.SUCCESS, response.body(), "SUCCESS !")
        } else {
            Resource(State.ERROR, null, "ERROR !")
        }
    }

    suspend fun remitSavings(request: SavingsRemitRequest): Resource<SavingsRemitResponse> {
        val response = apiService.remitSavings(request)
        return if (response.isSuccessful) {
            Resource(State.SUCCESS, response.body(), "SUCCESS !")
        } else {
            Resource(State.ERROR, null, "ERROR !")
        }
    }

    suspend fun bonusSavings(groupId: Int, request: BonusSavingsRequest): Resource<BonusSavingsResponse> {
        val response = apiService.bonusSavings(groupId, request)
        return if (response.isSuccessful) {
            Resource(State.SUCCESS, response.body(), "SUCCESS !")
        } else {
            Resource(State.ERROR, null, "ERROR !")
        }
    }

    suspend fun getNotifications(groupId: Int): Resource<NotificationResponse> {
        val response = apiService.getNotifications(groupId)
        Log.d("알림", "알림코드 ${response.code()}")
        Log.d("알림", "알림메세지 ${response.message()}")
        return if (response.isSuccessful) {
            Resource(State.SUCCESS, response.body(),"SUCCESS!")
        } else{
            Resource(State.ERROR, null, "ERROR!")
        }
    }
}
