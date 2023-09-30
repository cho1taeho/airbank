package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.LoanResponse
import com.example.myapplication.repository.LoanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val loanRepository: LoanRepository
): ViewModel() {

    val loanData = MutableLiveData<LoanResponse.Data>()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _navigateToLoan = MutableLiveData<Boolean>()
    val navigateToLoan: LiveData<Boolean> get() = _navigateToLoan

    init {
        getLoan()
    }

    private fun getLoan() {
        viewModelScope.launch {
            try {
                val response = loanRepository.getLoan(1)
                if (response.isSuccessful) {
                    loanData.postValue(response.body()?.data)
                } else {
                    _error.value = "Server Response Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Network Error: ${e.localizedMessage}"
            }
        }
    }
}