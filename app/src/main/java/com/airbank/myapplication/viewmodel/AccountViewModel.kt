package com.airbank.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbank.myapplication.model.AccountCheckResponse
import com.airbank.myapplication.model.AccountHistoryCheckResponse
import com.airbank.myapplication.model.AccountRegisterRequest
import com.airbank.myapplication.model.AccountRegisterResponse
import com.airbank.myapplication.model.BonusRequest
import com.airbank.myapplication.model.BonusResponse
import com.airbank.myapplication.model.ConfiscationResponse
import com.airbank.myapplication.model.ConfiscationTransferRequest
import com.airbank.myapplication.model.ConfiscationTransferResponse
import com.airbank.myapplication.model.InterestRepaymentRequest
import com.airbank.myapplication.model.InterestRepaymentResponse
import com.airbank.myapplication.model.InterestResponse
import com.airbank.myapplication.model.Resource
import com.airbank.myapplication.model.State
import com.airbank.myapplication.model.TaxResponse
import com.airbank.myapplication.model.TaxTransferRequest
import com.airbank.myapplication.model.TaxTransferResponse
import com.airbank.myapplication.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _accountRegisterState = MutableStateFlow<Resource<AccountRegisterResponse>>(Resource(State.LOADING, null, null))
    val accountRegisterState: StateFlow<Resource<AccountRegisterResponse>> get() = _accountRegisterState

    init{

    }

    fun accountRegister(request: AccountRegisterRequest) = viewModelScope.launch {
        _accountRegisterState.emit(Resource(State.LOADING, null, null))
        try {
            val response = accountRepository.accountRegister(request)
            _accountRegisterState.emit(response)
        } catch (e: Exception) {
            _accountRegisterState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }
    }

    private val _accountHistoryState = MutableStateFlow<Resource<AccountHistoryCheckResponse>>(Resource(State.LOADING, null, null))
    val accountHistoryState: StateFlow<Resource<AccountHistoryCheckResponse>> get() = _accountHistoryState

    fun accountHistory(accountType: String) = viewModelScope.launch {
        _accountHistoryState.emit(Resource(State.LOADING, null, null))
        try {
            val response = accountRepository.accountHistory(accountType)
            _accountHistoryState.emit(response)
        } catch (e: Exception) {
            _accountHistoryState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }
    }

    private val _accountCheckState = MutableStateFlow<Resource<AccountCheckResponse>>(Resource(State.LOADING, null, null))
    val accountCheckState: StateFlow<Resource<AccountCheckResponse>> get() = _accountCheckState

    fun accountCheck() = viewModelScope.launch {
        _accountCheckState.emit(Resource(State.LOADING, null, null))
        try {
            val response = accountRepository.accountCheck()
            _accountCheckState.emit(response)
        } catch (e: Exception) {
            _accountCheckState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }
    }



    private val _taxCheckState = MutableStateFlow<Resource<TaxResponse>>(Resource(State.LOADING, null, null))
    val taxCheckState: StateFlow<Resource<TaxResponse>> get() = _taxCheckState

    fun taxCheck(groupId: Int) = viewModelScope.launch {
        _taxCheckState.emit(Resource(State.LOADING, null, null))
        try {
            val response = accountRepository.taxCheck(groupId)
            _taxCheckState.emit(response)
        } catch (e: Exception) {
            _taxCheckState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }
    }

    private val _taxTransferState = MutableStateFlow<Resource<TaxTransferResponse>>(Resource(State.LOADING, null, null))
    val taxTransferState: StateFlow<Resource<TaxTransferResponse>> get() = _taxTransferState

    fun taxTransfer(request: TaxTransferRequest) = viewModelScope.launch {
        _taxTransferState.emit(Resource(State.LOADING, null, null))
        try {
            val response = accountRepository.taxTransfer(request)
            _taxTransferState.emit(response)
        } catch (e: Exception) {
            _taxTransferState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }
    }

    private val _bonusTransferState = MutableStateFlow<Resource<BonusResponse>>(Resource(State.LOADING, null, null))
    val bonusTransferState: StateFlow<Resource<BonusResponse>> get() = _bonusTransferState

    fun bonusTransfer(groupId: Int, request: BonusRequest) = viewModelScope.launch {
        _bonusTransferState.emit(Resource(State.LOADING, null, null))
        try {
            val response = accountRepository.bonusTransfer(groupId, request)
            _bonusTransferState.emit(response)
        } catch (e: Exception) {
            _bonusTransferState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }
    }


    private val _interestCheckState = MutableStateFlow<Resource<InterestResponse>>(Resource(State.LOADING, null, null))
    val interestCheckState: StateFlow<Resource<InterestResponse>> get() = _interestCheckState

    fun interestCheck(groupId: Int) = viewModelScope.launch {
        _interestCheckState.emit(Resource(State.LOADING, null, null))
        try {
            val response = accountRepository.interestCheck(groupId)
            _interestCheckState.emit(response)
            Log.d("이자뷰","이자뷰 ${response?.data?.code}")
        } catch (e: Exception) {
            _interestCheckState.emit(Resource(State.ERROR, null, e.localizedMessage))
            Log.d("이자뷰실패","이자뷰실패")
        }
    }

    private val _interestRepaymentState = MutableStateFlow<Resource<InterestRepaymentResponse>>(Resource(State.LOADING, null, null))
    val interestRepaymentState: StateFlow<Resource<InterestRepaymentResponse>> get() = _interestRepaymentState

    fun interestRepayment(request: InterestRepaymentRequest) = viewModelScope.launch {
        _interestRepaymentState.emit(Resource(State.LOADING, null, null))
        try {
            val response = accountRepository.interestRepayment(request)

            _interestRepaymentState.emit(response)
        } catch (e: Exception) {
            _interestRepaymentState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }
    }

    private val _confiscationCheckState = MutableStateFlow<Resource<ConfiscationResponse>>(Resource(State.LOADING, null, null))
    val confiscationCheckState: StateFlow<Resource<ConfiscationResponse>> get() = _confiscationCheckState

    fun confiscationCheck(groupId: Int) = viewModelScope.launch {
        _confiscationCheckState.emit(Resource(State.LOADING, null, null))
        try {
            val response = accountRepository.confiscationCheck(groupId)
            _confiscationCheckState.emit(response)
        } catch (e: Exception) {
            _confiscationCheckState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }
    }

    private val _confiscationTransferState = MutableStateFlow<Resource<ConfiscationTransferResponse>>(Resource(State.LOADING, null, null))
    val confiscationTransferState: StateFlow<Resource<ConfiscationTransferResponse>> get() = _confiscationTransferState

    fun confiscationTransfer(request: ConfiscationTransferRequest) = viewModelScope.launch {
        _confiscationTransferState.emit(Resource(State.LOADING, null, null))
        try {
            val response = accountRepository.confiscationTransfer(request)
            _confiscationTransferState.emit(response)
        } catch (e: Exception) {
            _confiscationTransferState.emit(Resource(State.ERROR, null, e.localizedMessage))
        }
    }
}