package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.CreateSavingsItemRequest
import com.example.myapplication.model.SavingsResponse
import com.example.myapplication.network.RetrofitBuilder
import kotlinx.coroutines.launch

class SavingsViewModel : ViewModel() {
    private val apiService = RetrofitBuilder.createApiService()
    val savingsData = MutableLiveData<SavingsResponse.Data>()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    init {
        getSavings()
    }
    private fun getSavings() {
        val groupId = 1

        viewModelScope.launch {
            try {
                val response = apiService.getSavings(groupId)
                if (response.isSuccessful) {
                    Log.d("SavingsViewModel", "Response: ${response.body()}")
                    savingsData.postValue(response.body()?.data)
                } else {
                    val errorMsg = "Server Response Error: ${response.code()}"
                    Log.e("SavingsViewModel", errorMsg)
                    _error.value = errorMsg
                }
            } catch (e: Exception) {
                _error.value = "Network Error: ${e.localizedMessage}"
            }
        }
    }

    private val _navigateToSavings = MutableLiveData<Boolean>()
    val navigateToSavings: LiveData<Boolean> get() = _navigateToSavings

    fun createSavingsItem(request: CreateSavingsItemRequest) {
        viewModelScope.launch {
            try {
                val response = apiService.createSavingsItem(request)
                if (response.isSuccessful && response.body()?.data?.id == 1) {
                    _navigateToSavings.postValue(true)
                } else {
                    _error.value = "Server Response Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Network Error: ${e.localizedMessage}"
            }
        }
    }

    fun onNavigateToSavingsDone() {
        _navigateToSavings.value = false
    }
}
