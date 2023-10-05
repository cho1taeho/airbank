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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.AirbankApplication
import com.example.myapplication.viewmodel.AccountViewModel
import com.example.myapplication.viewmodel.LoanViewModel
import com.example.myapplication.viewmodel.SavingsViewModel
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat

@Composable
fun NotificationScreen(navController: NavController) {
    val viewModel: SavingsViewModel = hiltViewModel()
    val notificationData by viewModel.getNotificationsState.collectAsState(initial = null)

    notificationData?.data?.data?.notifications?.let { notifications ->
        if (notifications.isNotEmpty()) {
            val groupedByDate = notifications.groupBy { it.createdAt.toString().substring(0, 10) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                for ((date, dailyNotifications) in groupedByDate) {
                    Text(
                        text = date,
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(color = Color.Black)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dailyNotifications.size * 40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color(0xFF00D2F3))
                            .clickable {

                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            for (notification in dailyNotifications) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(8.dp)
                                        .background(Color(0xFF00D2F3), RoundedCornerShape(50))
                                        .size(40.dp)
                                ) {
                                    Icon(
                                        painter = getImageForNotificationType(notification.notificationType),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(3f),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "${notification.notificationType}",
                                        style = TextStyle(color = Color.Black),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold

                                    )
                                    Text(
                                        "${notification.content}",
                                        style = TextStyle(color = Color.Black),
                                        fontSize = 11.sp
                                    )
                                    Text(
                                        hoursAgo(notification.createdAt),
                                        style = TextStyle(
                                            color = Color.Gray,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else{
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "알림이 없습니다", style = TextStyle(color = Color.Gray, fontSize = 16.sp))
            }
        }
    }?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "알림이 없습니다", style = TextStyle(color = Color.Gray, fontSize = 16.sp))
        }
    }
}

@Composable
fun getImageForNotificationType(type: String): Painter {
    return when (type) {
        "TAX", "CONFISCATION" -> painterResource(id = R.drawable.tax)
        "INTEREST","LOAN" -> painterResource(id = R.drawable.loancoin)
        "BONUS" -> painterResource(id = R.drawable.pinmoney)
        "ALLOWANCE" -> painterResource(id = R.drawable.pinmoney)
        "SAVINGS", "SAVINGS_CONFIRM", "SAVINGS_REWARD_CONFIRM" -> painterResource(id = R.drawable.savingscoin)
        "GROUP", "GROUP_CONFIRM" -> painterResource(id = R.drawable.group)

        else -> painterResource(id = R.drawable.plus)
    }
}



fun hoursAgo(time: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
    val date: Date? = inputFormat.parse(time)

    if (date != null) {
        val now = Calendar.getInstance()
        val timeEvent = Calendar.getInstance().apply { this.time = date }  // 수정된 부분

        if (now.get(Calendar.DATE) == timeEvent.get(Calendar.DATE)) {
            val diffHours = now.get(Calendar.HOUR_OF_DAY) - timeEvent.get(Calendar.HOUR_OF_DAY)
            if (diffHours < 1) {
                val diffMinutes = now.get(Calendar.MINUTE) - timeEvent.get(Calendar.MINUTE)
                return "$diffMinutes 분 전"
            }
            return "$diffHours 시간 전"
        } else {
            return "${timeEvent.get(Calendar.HOUR_OF_DAY)}시 ${timeEvent.get(Calendar.MINUTE)}분"
        }
    }

    return "알 수 없음"
}

