package com.airbank.myapplication.api

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
import com.airbank.myapplication.model.LoanChargeRequest
import com.airbank.myapplication.model.LoanChargeResponse
import com.airbank.myapplication.model.LoanRepaymentRequest
import com.airbank.myapplication.model.LoanRepaymentResponse
import com.airbank.myapplication.model.LoanResponse
import com.airbank.myapplication.model.LoanStartRequest
import com.airbank.myapplication.model.LoanStartResponse
import com.airbank.myapplication.model.NotificationResponse
import com.airbank.myapplication.model.SavingsRemitRequest
import com.airbank.myapplication.model.SavingsRemitResponse
import com.airbank.myapplication.model.SavingsResponse
import com.airbank.myapplication.model.TaxResponse
import com.airbank.myapplication.model.TaxTransferRequest
import com.airbank.myapplication.model.TaxTransferResponse
import com.airbank.myapplication.model.UpdateSavingsRequest
import com.airbank.myapplication.model.UpdateSavingsResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface ApiService {
    @GET("/savings/current")
    suspend fun getSavings(@Query("group_id") groupId: Int): Response<SavingsResponse>

    @POST("/savings/item")
    suspend fun createSavingsItem(@Body request: CreateSavingsItemRequest): Response<CreateSavingsItemResponse>
    @PATCH("/savings/confirm")
    suspend fun updateSavings(@Query("group_id") groupId: Int, @Body request: UpdateSavingsRequest): Response<UpdateSavingsResponse>
    @PATCH("/savings/cancel")
    suspend fun cancelSavings(@Body request: CancelSavingsRequest) : Response<CancelSavingsResponse>

    @POST("/savings")
    suspend fun remitSavings(@Body request : SavingsRemitRequest) : Response<SavingsRemitResponse>

    @POST("/savings/reward")
    suspend fun bonusSavings(@Query("group_id") groupId: Int,@Body request: BonusSavingsRequest) : Response<BonusSavingsResponse>

    @GET("/loans")
    suspend fun getLoan(@Query("group_id") groupId: Int): Response<LoanResponse>

    @POST("/accounts")
    suspend fun accountRegister(@Body request: AccountRegisterRequest) : Response<AccountRegisterResponse>

    //계좌 내역 조회
    @GET("/accounts/history")
    suspend fun accountHistory(@Query("account_type") accountType: String): Response<AccountHistoryCheckResponse>

    //계좌 잔액 조회
    @GET("/accounts")
    suspend fun accountCheck(): Response<AccountCheckResponse>

    @GET("/funds/tax")
    suspend fun taxCheck(@Query("group_id") groupId: Int): Response<TaxResponse>

    @POST("/funds/tax")
    suspend fun taxTransfer(@Body request: TaxTransferRequest) : Response<TaxTransferResponse>

    @POST("/funds/bonus")
    suspend fun bonusTransfer(@Query("group_id") groupId: Int,@Body request: BonusRequest): Response<BonusResponse>


    @GET("/funds/interest")
    suspend fun interestCheck(@Query("group_id") groupId: Int): Response<InterestResponse>

    @POST("/funds/interest")
    suspend fun interestRepayment(@Body request: InterestRepaymentRequest): Response<InterestRepaymentResponse>

    @GET("/funds/confiscation")
    suspend fun confiscationCheck(@Query("group_id") groupId: Int): Response<ConfiscationResponse>

    @POST("/funds/confiscation")
    suspend fun confiscationTransfer(@Body request: ConfiscationTransferRequest): Response<ConfiscationTransferResponse>



    @POST("/loans")
    suspend fun loanStart(@Body request: LoanStartRequest): Response<LoanStartResponse>


    @POST("/loans/repaid")
    suspend fun loanRepayment(@Body request: LoanRepaymentRequest): Response<LoanRepaymentResponse>

    @POST("/loans/charge")
    suspend fun loanCharge(@Query("group_id") groupId: Int, @Body request: LoanChargeRequest): Response<LoanChargeResponse>

    @GET("/notification")
    suspend fun getNotifications(@Query("group_id") groupId: Int): Response<NotificationResponse>

}








