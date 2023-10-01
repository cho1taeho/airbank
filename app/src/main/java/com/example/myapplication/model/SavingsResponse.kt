package com.example.myapplication.model


data class SavingsResponse(
    val code: String,
    val message: String,
    val data: Data
) {
    data class Data(
        val id: Int,
        val myAmount: Int,
        val parentsAmount: Int,
        val monthlyAmount: Int,
        val startedAt: String,
        val expiredAt: String,
        val endedAt: String,
        val status: String,
        val delayCount: Int,
        val savingsItem: SavingsItem
    )

    data class SavingsItem(
        val name: String,
        val amount: Int,
        val imageUrl: String
    )
}

data class CreateSavingsItemRequest(
    val name: String,
    val amount: Int,
    val month: Int,
    val parentsAmount: Int,
    val imageUrl: String
)


data class CreateSavingsItemResponse(
    val code: String,
    val message: String,
    val data: SavingsItemData
)

data class SavingsItemData(
    val id: Int
)

data class UpdateSavingsRequest(
    val isAccept: Boolean
)

data class UpdateSavingsResponse(
    val code: String,
    val message: String,
    val data: Data
) {
    data class Data(
        val id: Int,
        val status: String
    )
}

data class CancelSavingsRequest(
    val id : Int
)

data class CancelSavingsResponse(
    val code : String,
    val messge : String,
    val data : Data
){
    data class Data(
        val id : Int,
        val status : String
    )
}

data class SavingsRemitRequest(
    val id : Int,
    val acount : Int
)

data class SavingsRemitResponse(
    val code : String,
    val message : String,
    val data : Data
){
    data class Data(
        val id : Int,
        val amount : Int,
        val apiCreateAt : String,
        val transactionType : String,
        val transactionDistinction : String,
        val transactionPartner : String
    )
}

data class BonusSavingsRequest(
    val id : Int,
    val amout : Int
)

data class BonusSavingsResponse(
    val code : String,
    val message : String,
    val data : Data
){
    data class Data(
        val id : Int,
        val amount : Int,
        val apiCreateAt : String,
        val transactionType : String,
        val transactionDistinction : String,
        val transactionPartner : String
    )
}
