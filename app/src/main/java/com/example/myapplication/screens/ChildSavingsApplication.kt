package com.example.myapplication.screens


import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.myapplication.R


@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun ChildSavingsApplication(navController: NavController) {
    var targetValue by remember { mutableStateOf(TextFieldValue()) }
    var priceValue by remember { mutableStateOf(TextFieldValue()) }
    var requestPriceValue by remember { mutableStateOf(TextFieldValue()) }

    val calendar: Calendar = Calendar.getInstance()
    var today by remember { mutableStateOf("") }
    today =
        "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)}"

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current // Composable 내에서 Context 얻기


    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)


    if (showDatePicker) {
        val datePickerDialog =
            DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
                showDatePicker = false
            }, year, month, day)
        datePickerDialog.show()
        showDatePicker = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Image(
                painter = painterResource(id = R.drawable.arrowleft),
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
                    .clickable {
                        navController.navigate("childSavings")
                    }
            )

            Text(
                "티끌 신청하기",
                fontSize = 20.sp
            )

            TextButton(
                onClick = {
                    navController.navigate("childsavings")
                },
            ) {
                Text("신청")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("목표")
        val targetMaxLength = 10
        TextField(
            value = targetValue,
            onValueChange = { newValue ->
                if (newValue.text.length <= targetMaxLength) {
                    targetValue = newValue
                }
            },
//            label = { Text("목표를 입력하세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFD6F2FF)),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),


            )

        Spacer(modifier = Modifier.height(20.dp))

        Text("가격")

        TextField(
            value = priceValue,
            onValueChange = { newValue ->
                priceValue = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFD6F2FF)),
//            label = { Text("가격을 입력하세요.") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("요청 금액")

        TextField(
            value = requestPriceValue,
            onValueChange = { newValue ->
                requestPriceValue = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFD6F2FF)),
//            label = { Text("요청 가격을 입력하세요.") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        val monthNumbers = (3..12).toList()
        var selectedMonths by remember { mutableStateOf(3) }
        var expanded by remember { mutableStateOf(false) }

        Text("기간: $today ~ $selectedDate")

        Button(onClick = { showDatePicker = true }) {
            Text("날짜 선택")
        }


        Spacer(modifier = Modifier.height(20.dp))

        Text("첨부하기")
    }
}



