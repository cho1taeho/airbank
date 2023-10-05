package com.example.myapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.model.PATCHGroupsFundRequest

@Composable
fun ChildRuleScreen( navController: NavController
) {
    var taxRate by remember { mutableStateOf("") }
    var allowanceAmount by remember { mutableStateOf("") }
    var allowanceDate by remember { mutableStateOf("") }
    var confiscationRate by remember { mutableStateOf("") }
    var loanLimit by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp, 16.dp)
    ) {
        Row() {
            OutlinedTextField(
                value = allowanceAmount,
                onValueChange = { allowanceAmount = it },
                label = { Text(text = "용돈") },
                modifier = Modifier
                    .widthIn(max = 150.dp)
                    .weight(1f)
            )
        }
        Row() {
            OutlinedTextField(
                value = allowanceDate,
                onValueChange = { allowanceDate = it },
                label = { Text(text = "용돈 지급일") },
                modifier = Modifier
                    .widthIn(max = 150.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // 숫자 키패드를 보이도록 설정
                ),
            )
            Spacer(modifier = Modifier.width(20.dp))
            OutlinedTextField(
                value = taxRate,
                onValueChange = { taxRate = it },
                label = { Text(text = "세율") },
                modifier = Modifier
                    .widthIn(max = 150.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // 숫자 키패드를 보이도록 설정
                ),
            )
        }
        Row() {
            OutlinedTextField(
                value = loanLimit,
                onValueChange = { loanLimit = it },
                label = { Text(text = "땡겨쓰기 한도") },
                modifier = Modifier
                    .widthIn(max = 150.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // 숫자 키패드를 보이도록 설정
                ),
            )
            Spacer(modifier = Modifier.width(20.dp))
            OutlinedTextField(
                value = confiscationRate,
                onValueChange = { confiscationRate = it },
                label = { Text(text = "압류") },
                modifier = Modifier
                    .widthIn(max = 150.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // 숫자 키패드를 보이도록 설정
                ),
            )
        }

        Button(
            onClick = {
                val fundRequest = PATCHGroupsFundRequest(
                    taxRate = taxRate.toInt(),
                    allowanceAmount = allowanceAmount.toInt(),
                    allowanceDate = allowanceDate.toInt(),
                    confiscationRate = confiscationRate.toInt(),
                    loanLimit = loanLimit.toInt()
                )
//                onFormSubmitted(fundRequest)
                TODO()
            },
            modifier = Modifier.padding(top = 16.dp)

        ) {
            Text("SUBMIT")
        }
    }
}

