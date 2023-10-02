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
import androidx.navigation.NavController
import com.example.myapplication.AirbankApplication

@Composable
fun ChildLoanStartScreen(navController: NavController) {

    Column {
        CreditScore()
        Spacer(modifier = Modifier.size(10.dp))
        LoanAmount()
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
        )
    }
}

@Composable
fun LoanAmount() {

    var requestPriceValue by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xffD6F2FF))
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(13.dp)
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    "땡겨쓸 금액",
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.size(8.dp))
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
}
