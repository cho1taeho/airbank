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
import com.example.myapplication.R
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.AirbankApplication
import com.example.myapplication.model.AccountHistoryCheckResponse
import com.example.myapplication.viewmodel.AccountViewModel
import com.example.myapplication.viewmodel.LoanViewModel
import com.example.myapplication.viewmodel.SavingsViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat
import com.example.myapplication.model.State

@Composable
fun ChildTransactionHistoryScreen(navController: NavController){
    val accountViewModel: AccountViewModel = hiltViewModel()
    val accountData by accountViewModel.accountHistoryState.collectAsState(initial = null)

    var selectedBox by remember { mutableStateOf(0) }

    LaunchedEffect(selectedBox) {
        when(selectedBox) {
            0 -> accountViewModel.accountHistory("main")
            1 -> accountViewModel.accountHistory("loan")
            2 -> accountViewModel.accountHistory("savings")
        }
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
        ){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(25.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selectedBox == 0) Color(0xFF00D2F3) else Color.White)
                    .clickable {
                        selectedBox = 0
                        accountViewModel.accountHistory("main")
                    }
            ){
                Text(
                    "지갑",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold

                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(25.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selectedBox == 1) Color(0xFF00D2F3) else Color.White)
                    .clickable {
                        selectedBox = 1
                        accountViewModel.accountHistory("loan")
                    }
            ){
                Text("땡겨쓰기",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(25.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selectedBox == 2) Color(0xFF00D2F3) else Color.White)
                    .clickable {
                        selectedBox = 2
                        accountViewModel.accountHistory("savings")
                    }
            ){
                Text("티끌모으기",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        when (selectedBox) {
            0 -> MainHistory()
            1 -> LoanHistory()
            2 -> SavingsHistory()
        }

    }
}

@Composable
fun MainHistory(){
    val accountViewModel: AccountViewModel = hiltViewModel()
    val accountData by accountViewModel.accountHistoryState.collectAsState(initial = null)

    LaunchedEffect(key1 = null){
        accountViewModel.accountHistory("main")
    }

    accountData?.data?.data?.accountHistoryElement?.let { transactions ->

        if (transactions.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                for (transaction in transactions) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFEDEDED)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = "금액: ${transaction.amount}",
                                style = TextStyle(color = Color.Black),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "날짜: ${transaction.apiCreatedAt}",
                                style = TextStyle(color = Color.Gray),
                                fontSize = 10.sp
                            )
                            Text(
                                text = "거래 유형: ${transaction.transactionType}",
                                style = TextStyle(color = Color.Gray),
                                fontSize = 10.sp
                            )
                        }
                    }
                }

            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "거래내역이 없습니다.", style = TextStyle(color = Color.Gray, fontSize = 16.sp))
            }
        }

    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "거래내역이 없습니다1.", style = TextStyle(color = Color.Gray, fontSize = 16.sp))
        }
    }
}

@Composable
fun LoanHistory() {
    val accountViewModel: AccountViewModel = hiltViewModel()
    val accountData by accountViewModel.accountHistoryState.collectAsState(initial = null)

    LaunchedEffect(key1 = null){
        accountViewModel.accountHistory("loan")
    }

    accountData?.data?.data?.accountHistoryElement?.let { transactions ->

        if (transactions.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                for (transaction in transactions) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFEDEDED)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = "금액: ${transaction.amount}",
                                style = TextStyle(color = Color.Black),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "날짜: ${transaction.apiCreatedAt}",
                                style = TextStyle(color = Color.Gray),
                                fontSize = 10.sp
                            )
                            Text(
                                text = "거래 유형: ${transaction.transactionType}",
                                style = TextStyle(color = Color.Gray),
                                fontSize = 10.sp
                            )
                        }
                    }
                }

            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "거래내역이 없습니다.", style = TextStyle(color = Color.Gray, fontSize = 16.sp))
            }
        }

    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "거래내역이 없습니다1.", style = TextStyle(color = Color.Gray, fontSize = 16.sp))
        }
    }
}

@Composable
fun SavingsHistory() {
    val accountViewModel: AccountViewModel = hiltViewModel()
    val accountData by accountViewModel.accountHistoryState.collectAsState(initial = null)

    LaunchedEffect(key1 = null){
        accountViewModel.accountHistory("savings")
    }

    accountData?.data?.data?.accountHistoryElement?.let { transactions ->

        if (transactions.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                for (transaction in transactions) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFEDEDED)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = "금액: ${transaction.amount}",
                                style = TextStyle(color = Color.Black),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "날짜: ${transaction.apiCreatedAt}",
                                style = TextStyle(color = Color.Gray),
                                fontSize = 10.sp
                            )
                            Text(
                                text = "거래 유형: ${transaction.transactionType}",
                                style = TextStyle(color = Color.Gray),
                                fontSize = 10.sp
                            )
                        }
                    }
                }

            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "거래내역이 없습니다.", style = TextStyle(color = Color.Gray, fontSize = 16.sp))
            }
        }

    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "거래내역이 없습니다1.", style = TextStyle(color = Color.Gray, fontSize = 16.sp))
        }
    }

}

