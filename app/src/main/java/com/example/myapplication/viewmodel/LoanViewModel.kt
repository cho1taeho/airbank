package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.AirbankApplication
import com.example.myapplication.model.CreateSavingsItemResponse
import com.example.myapplication.model.LoanChargeRequest
import com.example.myapplication.model.LoanChargeResponse
import com.example.myapplication.model.LoanRepaymentRequest
import com.example.myapplication.model.LoanRepaymentResponse
import com.example.myapplication.model.LoanResponse
import com.example.myapplication.model.LoanStartRequest
import com.example.myapplication.model.LoanStartResponse
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
//        val groupId = AirbankApplication.prefs.getString("group_id", "")
        val groupId = "1"
        try {
            val response = loanRepository.getLoan(groupId.toInt())
            _loanState.emit(response)
            Log.d("DEBUG", "_loanState is set: ${_loanState.value}")
        } catch (e: Exception) {
            _loanState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }

    }
    private val _loanStartState = MutableStateFlow<Resource<LoanStartResponse>>(Resource(State.LOADING, null, null))
    val loanStartState: StateFlow<Resource<LoanStartResponse>> get() = loanStartState

    fun loanStart(request: LoanStartRequest) = viewModelScope.launch{
        _loanStartState.emit(Resource(State.LOADING, null,null))
        try {
            val response = loanRepository.loanStart(request)
            _loanStartState.emit(response)
        } catch(e:Exception) {
            _loanStartState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }


    }

    private val _loanRepayment = MutableStateFlow<Resource<LoanRepaymentResponse>>(Resource(State.LOADING, null, null))
    val loanRepayment: StateFlow<Resource<LoanRepaymentResponse>> get() = loanRepayment

    fun loanRepayment(request: LoanRepaymentRequest) = viewModelScope.launch{
        _loanRepayment.emit(Resource(State.LOADING, null,null))
        try {
            val response = loanRepository.loanRepayment(request)
            _loanRepayment.emit(response)
        } catch(e:Exception) {
            _loanRepayment.emit(Resource(State.ERROR, null, e.localizedMessage))
        }


    }

    private val _loanCharge = MutableStateFlow<Resource<LoanChargeResponse>>(Resource(State.LOADING, null, null))
    val loanCharge: StateFlow<Resource<LoanChargeResponse>> get() = loanCharge

    fun loanCharge(groupId:Int, request: LoanChargeRequest) = viewModelScope.launch{
        _loanCharge.emit(Resource(State.LOADING, null,null))
        try {
            val response = loanRepository.loanCharge(groupId,request)
            _loanCharge.emit(response)
        } catch(e:Exception) {
            _loanCharge.emit(Resource(State.ERROR, null, e.localizedMessage))
        }


    }



}