package com.airbank.myapplication.screens

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
import android.net.Uri
import android.net.http.UrlRequest.Status
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.airbank.myapplication.R
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import coil.compose.AsyncImage
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbank.myapplication.AirbankApplication
import com.airbank.myapplication.model.BonusRequest
import com.airbank.myapplication.model.ConfiscationTransferRequest
import com.airbank.myapplication.model.InterestRepaymentRequest
import com.airbank.myapplication.viewmodel.AccountViewModel
import com.airbank.myapplication.viewmodel.LoanViewModel
import com.airbank.myapplication.viewmodel.SavingsViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat
import com.airbank.myapplication.model.State

@Composable
fun ChildInterestRespaymentScreen(navController: NavController){
    val accountViewModel: AccountViewModel = hiltViewModel()
    val interestData by accountViewModel.interestCheckState.collectAsState(initial = null)
    var repaymentAmount by remember {mutableStateOf("")}
    val groupId = com.airbank.myapplication.AirbankApplication.prefs.getString("group_id","")

    LaunchedEffect(Unit){
        if (groupId.isNotEmpty()) {
            accountViewModel.interestCheck(groupId.toInt())
        }
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        Text(
            "이번달 이자를 송금해주세요 ",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(30.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(color = Color.White)
        ) {
            TextField(
                value = repaymentAmount,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        repaymentAmount = newValue
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("이번달 이자 입력") },
//                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            "이번달 이자 : ${interestData?.data?.data?.amount ?: 0}원",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xFF00D2F3))
                .clickable {
                    navController.navigate(BottomNavItem.Wallet.screenRoute)
                    val request = InterestRepaymentRequest(repaymentAmount.toInt())
                    accountViewModel.interestRepayment(request)

                }
        ) {
            Text(
                "이번달 이자 송금하기",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}