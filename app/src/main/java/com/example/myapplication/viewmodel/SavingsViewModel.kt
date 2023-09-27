package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.BonusSavingsRequest
import com.example.myapplication.model.CancelSavingsRequest
import com.example.myapplication.model.CreateSavingsItemRequest
import com.example.myapplication.model.SavingsRemitRequest
import com.example.myapplication.model.SavingsResponse
import com.example.myapplication.model.UpdateSavingsRequest
import com.example.myapplication.repository.SavingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SavingsViewModel @Inject constructor(
    private val savingsRepository: SavingsRepository
) : ViewModel() {

    val savingsData = MutableLiveData<SavingsResponse.Data>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _navigateToSavings = MutableLiveData<Boolean>()
    val navigateToSavings: LiveData<Boolean> get() = _navigateToSavings

    init {
        getSavings()
    }
    private fun getSavings() {
        viewModelScope.launch {
            try {
                val response = savingsRepository.getSavings(1 )
                if (response.isSuccessful) {
                    savingsData.postValue(response.body()?.data)
                } else {
                    _error.value = "Server Response Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Network Error: ${e.localizedMessage}"
            }
        }
    }

    fun createSavingsItem(request: CreateSavingsItemRequest) {
        viewModelScope.launch {
            try {
                val response = savingsRepository.createSavingsItem(request)
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


    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> get() = _updateSuccess

    fun updateSavings(groupId: Int, request: UpdateSavingsRequest) {
        viewModelScope.launch{
            try {
                val response = savingsRepository.updateSavings(groupId, request)
                if (response.isSuccessful && response.body()?.data?.status == "PROCEEDING") {
                    _updateSuccess.postValue(true)
                } else {
                    _error.value = "Server Response Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Network Error: ${e.localizedMessage}"
            }
        }
    }

    private val _cancelSuccess = MutableLiveData<Boolean>()
    val cancelSuccess: LiveData<Boolean> get() = _cancelSuccess

    fun cancelSavings(request : CancelSavingsRequest) {
        viewModelScope.launch{
            try {
                val response = savingsRepository.cancelSavings(request)
                if (response.isSuccessful && response.body()?.data?.status == "FAIL") {
                    _cancelSuccess.postValue(true)
                } else {
                    _error.value = "Server Response Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Network Error: ${e.localizedMessage}"
            }
        }
    }

//    fun remitSavings(request: SavingsRemitRequest) {
//        viewModelScope.launch{
//            try {
//                val response = savingsRepository.remitSavings(request)
//                if (response.isSuccessful) {
//
//                }else {
//                    _error.value = "Server Response Error: ${response.code()}"
//                }
//            } catch (e: Exception) {
//                _error.value = "Network Error: ${e.localizedMessage}"
//            }
//        }
//    }
//
//    fun bonusSavings(groupId: Int, request: BonusSavingsRequest) {
//        viewModelScope.launch{
//            try {
//                val response = savingsRepository.bonusSavings(groupId, request)
//                if (response.isSuccessful) {
//
//                } else {
//                    _error.value = "Server Response Error: ${response.code()}"
//                }
//            } catch (e: Exception) {
//                _error.value = "Network Error: ${e.localizedMessage}"
//            }
//        }
//    }
}