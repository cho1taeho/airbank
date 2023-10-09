//@file:Suppress("DEPRECATION")

package com.example.myapplication.screens

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll

import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.AirbankApplication
import com.example.myapplication.model.GETCreditHistoryResponse
import com.example.myapplication.network.HDRetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@Composable
fun CreditScoreBox() {

    val viewModel : CreditScoreViewModel = viewModel()
    LaunchedEffect(Unit){
        val groupid =  AirbankApplication.prefs.getString("group_id","")
        if (groupid.isNotEmpty()){
            viewModel.getCreditHistory(groupid.toInt())
        }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(252.dp)
            .background(Color.White , RoundedCornerShape(size = 22.dp))
            .padding(start = 18.dp, top = 20.dp, end = 18.dp, bottom = 20.dp)
    ) {
        Text("철수님의 신용점수")
        var creditPoint = 732
        ScoreBar(creditPoint)
        PerformanceChart2(com.example.myapplication.Util.sortedCreditHistories)

    }
}



@Composable
fun PerformanceChart2(data: List<GETCreditHistoryResponse.Data.creditHistory>) {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())

    val parsedData = data.map { (creditScore, createdAt) ->
        val date = simpleDateFormat.parse(createdAt) ?: Date()
        Pair(date, creditScore + 0f)
    }
        .plus(Pair(Date(),(data.last().creditScore+0f)))



    val density = LocalDensity.current

    val minDate = parsedData.minByOrNull { it.first }?.first ?: Date()
    val maxDate = parsedData.maxByOrNull { it.first }?.first ?: Date()

    Log.d("Chart","Data= $parsedData")

    val days = ((maxDate.time / (1000*24*60*60)) - (minDate.time / (1000*24*60*60))).toFloat()
    val xStep = 150f

    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 10.sp.toPx() }
        }
    }
    val scrollState = rememberScrollState(initial = (xStep*days).toInt())
    Box(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .fillMaxSize()
            .zIndex(2f)
    ){
        Canvas(
            modifier = Modifier
                .width(((xStep * (days)) / 3 + 200f).dp)
                .fillMaxHeight()
//                .background(Color.Green)
        ) {
            Log.d("Chart size", "calc= ${xStep * (days)+200f} => ${(xStep * (days)+200f).dp},  width = ${size.width}")
            val yStep = (size.height-150f) / 5
            // x-축과 y-축의 시작점을 계산합니다
            val xAxisStart = Offset(scrollState.value.toFloat()+100f,  size.height-100f)
            val yAxisStart = Offset(scrollState.value.toFloat()+100f,  size.height-100f)

            // x-축과 y-축을 그립니다
            drawLine(color = Color.Black, start = xAxisStart, end = Offset(size.width+100f, size.height-100f), strokeWidth = 1f)
            drawLine(color = Color.Black, start = yAxisStart, end = Offset(scrollState.value.toFloat()+100f, size.height - yStep*5 -100f), strokeWidth = 1f)
//          // x축은 날짜별로 다 그려놓고, 포인트를 따로찍자




            for (i in 1 until 6) {
                val yValue = yAxisStart.y - (i * yStep)
                Log.d("Chart", "yValue=$yValue")

                drawContext.canvas.nativeCanvas.drawText(
                    "${(i) * 200}",
                    scrollState.value + 50f,
                    yValue,
                    textPaint
                )
            }

            for(i in (minDate.time/(24*60*60*1000L)) .. maxDate.time/(24*60*60*1000L) ){
                val date = Date(i*24*60*60*1000)
                Log.d("Chart xaxis",date.toString())
                val x = (date.time / (24 * 60 * 60 * 1000) - minDate.time / (24 * 60 * 60 * 1000)).toFloat() * xStep + 200f
                if (x >= scrollState.value.toFloat() + 100f && x <= size.width - 100f) {
                    drawContext.canvas.nativeCanvas.drawText(
                        SimpleDateFormat("MM/dd", Locale.getDefault()).format(date),
                        (date.time / (24 * 60 * 60 * 1000) - minDate.time / (24 * 60 * 60 * 1000)).toFloat() * xStep + 200f,
                        yAxisStart.y + 40f,
                        textPaint
                    )
                }
            }
            var previousX = (parsedData.firstOrNull()?.first?.time?.toFloat() ?: 0f) / (24 * 60 * 60 * 1000) * xStep + 100f
            var previousY = (yAxisStart.y - (parsedData.firstOrNull()?.second ?: 0f) * (yStep * 5) / 1000)

            parsedData.forEachIndexed { index, (date, value) ->
                Log.d("Chart","index: $index, date: $date, value: $value")
                val x = (date.time / (24 * 60 * 60 * 1000) - minDate.time / (24 * 60 * 60 * 1000)).toFloat() * xStep + 200f
                val y = yAxisStart.y - value * (yStep * 5) / 1000

//                if (x >= scrollState.value.toFloat() + 100f && x <= scrollState.value.toFloat() + size.width - 100f)  {
                // 그래프의 x 좌표가 화면 내부에 있을 때만 그리기
                if (index > 0) {
                        drawLine(
                            Color(0xff5FCFEF),
                            start = Offset(previousX, previousY),
                            end = Offset(x, y),
                            strokeWidth = 5f
                        )
                    }
                previousX = x
                previousY = y
                }
//            }
        }

    }
}


class CreditScoreViewModel @Inject constructor() : ViewModel() {

    var creditHistories: List<GETCreditHistoryResponse.Data.creditHistory> = emptyList()
    fun getCreditHistory(groupid: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val response = HDRetrofitBuilder.HDapiService().getCreditHistory(groupid)
            if (response.isSuccessful){
                val getresponse = response.body()
//                if(getresponse != null){
//                    creditHistories = getresponse.data.creditHistories
//                }
            }
        }
    }
}