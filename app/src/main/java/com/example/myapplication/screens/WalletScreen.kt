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
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat

@Composable
fun WalletScreen(navController: NavController) {


    Column (
        modifier = Modifier
            .padding(16.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xffD6F2FF))
                .padding(horizontal = 16.dp)
        ){
            Column (
                modifier = Modifier
                    .padding(start = 13.dp)
            ){
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    "나의 잔액",
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "20,000,000",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    "팡팡은행 이현도 님의 통장"
                )
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
//                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xffD6F2FF))
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
            ){
                Spacer(modifier = Modifier.size(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.moneysend),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))

                Text("자녀에게 추가금액 보내기")
                
                Spacer(modifier = Modifier.weight(1f))
                
                Image(
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)      
                )
                
            }
        }
        Spacer(modifier = Modifier.size(17.dp))
        Divider(
            color = Color.Black,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.size(17.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xFFE4EBED))
                .padding(horizontal = 16.dp)

        ){
            Column (
                modifier = Modifier
                    .padding(start = 16.dp)
            ){
                Spacer(modifier = Modifier.size(10.dp))
                Text("레이첼님의 기본 용돈")
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "월 100,000원",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text("매월 10일 자동이체")
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xFFE4EBED))
                .padding(horizontal = 16.dp)
        ){
            Column {
                Spacer(modifier = Modifier.size(10.dp))
                Text("레이첼님의 신용점수")
                ScoreBar(score = 512)
                CreditPoint()
            }
        }
    }
}


@Preview
@Composable
fun CreditPoint() {
    val list = listOf(500f, 600f, 450f, 480f, 650f, 700f)
    val zipList: List<Pair<Float, Float>> = list.zipWithNext()
    val xAxisLabels = getMonths()
    val yAxisLabels = List((1000 / 200) + 1) { it * 200 }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val max = list.maxOrNull() ?: 0f
        val min = 0f
        val lineColor = if (list.last() > list.first()) Color.Green else Color.Red

        // Y Axis Labels
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            yAxisLabels.reversed().forEach { label ->
                Text(text = label.toString(), modifier = Modifier.padding(end = 4.dp))
            }
        }

        // Graph, X Axis Labels, and Axis Lines
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxSize()
                .padding(start = 32.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .align(Alignment.TopStart),
                onDraw = { drawRect(color = Color.Black) }
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for ((index, pair) in zipList.withIndex()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 16.dp), // Making space for X Axis Labels
                            onDraw = {
                                val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
                                val toValuePercentage = getValuePercentageForRange(pair.second, max, min)
                                val fromPoint = Offset(x = 0f, y = size.height.times(1 - fromValuePercentage))
                                val toPoint = Offset(x = size.width, y = size.height.times(1 - toValuePercentage))

                                drawLine(
                                    color = lineColor,
                                    start = fromPoint,
                                    end = toPoint,
                                    strokeWidth = 3f
                                )
                            }
                        )
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .align(Alignment.BottomStart),
                            onDraw = { drawRect(color = Color.Black) }
                        )
                        Text(
                            text = xAxisLabels.getOrNull(index) ?: "",
                            modifier = Modifier.align(Alignment.BottomStart)
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun getMonths(): List<String> {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("MMM", Locale.getDefault())
    return List(6) {
        val month = dateFormat.format(calendar.time)
        calendar.add(Calendar.MONTH, -1)
        month
    }.reversed()
}

fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float {
    if (max - min == 0f) return 0f
    return (value - min) / (max - min)
}