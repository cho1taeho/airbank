package com.airbank.myapplication.repository

import android.util.Log
import com.airbank.myapplication.api.ApiService
import com.airbank.myapplication.model.LoanChargeRequest
import com.airbank.myapplication.model.LoanChargeResponse
import com.airbank.myapplication.model.LoanRepaymentRequest
import com.airbank.myapplication.model.LoanRepaymentResponse
import com.airbank.myapplication.model.LoanResponse
import com.airbank.myapplication.model.LoanStartRequest
import com.airbank.myapplication.model.LoanStartResponse
import com.airbank.myapplication.model.Resource
import com.airbank.myapplication.model.State
import retrofit2.Response
import javax.inject.Inject

class LoanRepository @Inject constructor(
    private val apiService: ApiService
){



    suspend fun getLoan(groupId: Int): Resource<LoanResponse> {
        val response = apiService.getLoan(groupId)
        return if (response.isSuccessful) {
            Resource(State.SUCCESS, response.body(), "SUCCESS")
        } else {
            Resource(State.ERROR, null, "ERROR")
        }
    }


    suspend fun loanStart(request: LoanStartRequest): Resource<LoanStartResponse>{
        val response = apiService.loanStart(request)
        return if (response.isSuccessful){
            Resource(State.SUCCESS,response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR,null,"ERROR")
        }
    }

    suspend fun loanRepayment(request: LoanRepaymentRequest): Resource<LoanRepaymentResponse>{
        val response = apiService.loanRepayment(request)
        return if (response.isSuccessful){
            Resource(State.SUCCESS,response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR,null,"ERROR")
        }
    }

    suspend fun loanCharge(groupId: Int, request: LoanChargeRequest): Resource<LoanChargeResponse>{
        val response = apiService.loanCharge(groupId,request)
        return if (response.isSuccessful){
            Resource(State.SUCCESS,response.body(),"SUCCESS")
        } else{
            Resource(State.ERROR,null,"ERROR")
        }
    }






}