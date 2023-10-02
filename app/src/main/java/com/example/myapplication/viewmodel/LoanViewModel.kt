package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.LoanResponse
import com.example.myapplication.model.Resource
import com.example.myapplication.model.State
import com.example.myapplication.repository.LoanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val loanRepository: LoanRepository
) : ViewModel() {

    private val _loanState =
        MutableStateFlow<Resource<LoanResponse>>(Resource(State.LOADING, null, null))

    val loanState: StateFlow<Resource<LoanResponse>> get() = _loanState
    init {
        getLoan()
    }

    fun getLoan() = viewModelScope.launch {
        _loanState.emit(Resource(State.LOADING, null, null))
        try {
            val response = loanRepository.getLoan(1)
            _loanState.emit(response)
            Log.d("DEBUG", "_loanState is set: ${_loanState.value}")
        } catch (e: Exception) {
            _loanState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }

    }
}