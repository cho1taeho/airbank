package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.AirbankApplication
import com.example.myapplication.viewmodel.LoanViewModel

@Composable
fun ChildLoanStartScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        CreditScore()
        Spacer(modifier = Modifier.padding(10.dp))
        LoanAmount()
        Spacer(modifier = Modifier.padding(10.dp))
        LoanDetail()
    }
}

@Composable
fun CreditScore() {

    Column(
    ) {
        val creditPoint = AirbankApplication.prefs.getString("creditScore", "")
        Text(

            "신용점수: ${creditPoint}p",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun LoanAmount() {

    var requestPriceValue by remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xffD6F2FF))
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(13.dp)
        ) {
            Text(
                "땡겨쓸 금액",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextField(
                value = requestPriceValue,
                onValueChange = { newValue ->
                    requestPriceValue = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFD6F2FF)),
                label = { Text("요청 가격을 입력하세요.") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

@Composable
fun LoanDetail() {
    val loanViewModel: LoanViewModel = hiltViewModel()
    val loanData by loanViewModel.loanState.collectAsState()

    Column() {
        Text(
            "변동사항",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        loanData?.let { data ->
            val loanLimit = data.data?.data?.loanLimit ?: 0
            val amount = data.data?.data?.amount ?: 0
            val remainingAmount = loanLimit - amount

            Column {
                Text(
                    "현도 금액: $loanLimit",
                    fontSize = 14.sp,
                    color = Color(0xff515151)
                )
                Text(
                    "사용 금액: $amount",
                    fontSize = 14.sp,
                    color = Color(0xff515151)
                )

                if (remainingAmount >= 0.0) {
                    Text(
                        "땡겨쓰기 가능 금액: $remainingAmount",
                        fontSize = 14.sp,
                        color = Color(0xff515151)
                    )
                } else {
                    Text(
                        "땡겨쓰기 가능 금액: 0.0",
                        fontSize = 14.sp,
                        color = Color(0xff515151)
                    )
                }
            }
        }
    }
}

