package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
@Composable
fun AddChildScreen(navController: NavController){
    var phoneNumberValue by remember { mutableStateOf("") }

    Column(modifier = Modifier) {
        Text("전화번호")
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = phoneNumberValue,
            onValueChange = { newValue ->
                // 숫자만 입력받도록 제한
                if (newValue.length <= 11 && newValue.isDigitsOnly()) {
                    // XXX-XXXX-XXXX 형식으로 포맷팅
                    phoneNumberValue = newValue
                }
            },
            label = { Text("전화번호를 입력하세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFD6F2FF)),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number // 숫자 키패드를 보이도록 설정
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
    }

}
@Preview
@Composable
fun UItest(){

}