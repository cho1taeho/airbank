package com.airbank.myapplication.repository


import android.util.Log
import com.airbank.myapplication.api.ApiService
import com.airbank.myapplication.model.AccountCheckResponse
import com.airbank.myapplication.model.AccountHistoryCheckResponse
import com.airbank.myapplication.model.AccountRegisterRequest
import com.airbank.myapplication.model.AccountRegisterResponse
import com.airbank.myapplication.model.BonusRequest
import com.airbank.myapplication.model.BonusResponse
import com.airbank.myapplication.model.BonusSavingsRequest
import com.airbank.myapplication.model.BonusSavingsResponse
import com.airbank.myapplication.model.CancelSavingsRequest
import com.airbank.myapplication.model.CancelSavingsResponse
import com.airbank.myapplication.model.ConfiscationResponse
import com.airbank.myapplication.model.ConfiscationTransferRequest
import com.airbank.myapplication.model.ConfiscationTransferResponse
import com.airbank.myapplication.model.CreateSavingsItemRequest
import com.airbank.myapplication.model.CreateSavingsItemResponse
import com.airbank.myapplication.model.InterestRepaymentRequest
import com.airbank.myapplication.model.InterestRepaymentResponse
import com.airbank.myapplication.model.InterestResponse
import com.airbank.myapplication.model.Resource
import com.airbank.myapplication.model.SavingsRemitRequest
import com.airbank.myapplication.model.SavingsRemitResponse
import com.airbank.myapplication.model.SavingsResponse
import com.airbank.myapplication.model.State
import com.airbank.myapplication.model.TaxResponse
import com.airbank.myapplication.model.TaxTransferRequest
import com.airbank.myapplication.model.TaxTransferResponse
import com.airbank.myapplication.model.UpdateSavingsRequest
import com.airbank.myapplication.model.UpdateSavingsResponse
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun accountRegister(request: AccountRegisterRequest): Resource<AccountRegisterResponse> {
        val response = apiService.accountRegister(request)
        return if (response.isSuccessful){
            Resource(State.SUCCESS, response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR, null, "ERROR")
        }
    }

    suspend fun accountHistory(accountType: String): Resource<AccountHistoryCheckResponse>{
        val response = apiService.accountHistory(accountType)
        return if (response.isSuccessful){
            Resource(State.SUCCESS, response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR, null, "ERROR")
        }
    }

    suspend fun accountCheck(): Resource<AccountCheckResponse>{
        val response = apiService.accountCheck()
        return if (response.isSuccessful){
            Resource(State.SUCCESS, response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR,null,"ERROR")
        }
    }

    suspend fun taxCheck(groupId: Int): Resource<TaxResponse>{
        val response = apiService.taxCheck(groupId)
        return if (response.isSuccessful){
            Resource(State.SUCCESS,response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR, null, "ERROR")
        }
    }

    suspend fun taxTransfer(request: TaxTransferRequest): Resource<TaxTransferResponse>{
        val response = apiService.taxTransfer(request)
        return if (response.isSuccessful){
            Resource(State.SUCCESS, response.body(),"SUCCESS")
        } else {
            Resource(State.ERROR, null,"ERROR")
        }
    }

    suspend fun bonusTransfer(groupId: Int, request:BonusRequest): Resource<BonusResponse>{
        val response = apiService.bonusTransfer(groupId,request)
        return if (response.isSuccessful){
            Resource(State.SUCCESS, response.body(),"SUCCESS")
        } else {
            Resource(State.ERROR, null,"ERROR")
        }
    }

    suspend fun interestCheck(groupId: Int): Resource<InterestResponse>{
        val response = apiService.interestCheck(groupId)
        return if (response.isSuccessful){
            Resource(State.SUCCESS,response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR,null,"ERROR")
        }
    }

    suspend fun interestRepayment(request: InterestRepaymentRequest): Resource<InterestRepaymentResponse>{
        val response = apiService.interestRepayment(request)
        return if (response.isSuccessful){
            Resource(State.SUCCESS,response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR,null,"ERROR")
        }
    }

    suspend fun confiscationCheck(groupId: Int): Resource<ConfiscationResponse>{
        val response = apiService.confiscationCheck(groupId)
        return if (response.isSuccessful){
            Resource(State.SUCCESS,response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR,null,"ERROR")
        }
    }

    suspend fun confiscationTransfer(request: ConfiscationTransferRequest): Resource<ConfiscationTransferResponse>{
        val response = apiService.confiscationTransfer(request)
        return if (response.isSuccessful){
            Resource(State.SUCCESS,response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR,null,"ERROR")
        }
    }





}
