//@file:Suppress("DEPRECATION")

package com.airbank.myapplication.screens

import android.graphics.Paint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.asComposePaint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbank.myapplication.AirbankApplication
import com.airbank.myapplication.model.GETCreditHistoryResponse
import com.airbank.myapplication.network.HDRetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreditScoreBox(name: String) {
    val viewModel : CreditScoreViewModel = viewModel()
    LaunchedEffect(Unit){
        val groupid =  com.airbank.myapplication.AirbankApplication.prefs.getString("group_id","")
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
            .background(Color.White, RoundedCornerShape(size = 22.dp))
            .padding(start = 18.dp, top = 20.dp, end = 18.dp, bottom = 20.dp)
    ) {
        Text("${name}님의 신용점수")
        val data = com.airbank.myapplication.Util.sortedCreditHistories
        val lastCreditScore = com.airbank.myapplication.AirbankApplication.prefs.getString("creditScore",data.last().creditScore.toString()).toInt()
        ScoreBar(lastCreditScore)
        PerformanceChart2(data)

    }
}



@Composable
fun PerformanceChart2(data: List<GETCreditHistoryResponse.Data.creditHistory>) {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
    val lastCreditScore = com.airbank.myapplication.AirbankApplication.prefs.getString("creditScore",data.last().creditScore.toString()).toFloat()
    val parsedData = data.map { (creditScore, createdAt) ->
        val date = simpleDateFormat.parse(createdAt) ?: Date()
        Pair(date, creditScore + 0f)
    }
        .plus(Pair(simpleDateFormat.parse("2023-10-11T09:00:00.000000"),(970+0f)))
        .plus(Pair(simpleDateFormat.parse("2023-10-11T12:00:00.000000"),(880+0f)))
        .plus(Pair(simpleDateFormat.parse("2023-10-11T14:00:00.000000"),(lastCreditScore+0f)))



    val density = LocalDensity.current

    val minDate = parsedData.minByOrNull { it.first }?.first ?: Date()
    val maxDate = parsedData.maxByOrNull { it.first }?.first ?: Date()

    Log.d("Chart","Data= $parsedData")

    val days = ((maxDate.time / (1000*24*60*60)) - (minDate.time / (1000*24*60*60))).toFloat()
    val xStep = 100f

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
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        ){
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Log.d("Chart size", "calc= ${xStep * (days)+200f} => ${(xStep * (days)+200f).dp},  width = ${size.width}")
                val yStep = (size.height-150f) / 5

                val yAxisStart = Offset(100f,  size.height-100f)
                val xAxisStart = Offset(100f,  size.height-100f)
                drawLine(color = Color.Black, start = xAxisStart, end = Offset(size.width, size.height-100f), strokeWidth = 1f)

                drawLine(color = Color.Black, start = yAxisStart, end = Offset(100f, size.height - yStep*5 -100f), strokeWidth = 1f)




                for (i in 1 until 6) {
                    val yValue = yAxisStart.y - (i * yStep)
                    Log.d("Chart", "yValue=$yValue")

                    drawContext.canvas.nativeCanvas.drawText(
                        "${(i) * 200}",
                        50f,
                        yValue+0f,
                        textPaint
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .width((xStep*7/3).dp)
                .offset((150f/3).dp,0.dp)
                .horizontalScroll(scrollState)
                .zIndex(2f)
//                .background(Color.Red)
        ){
            Canvas(
                modifier = Modifier
                    .width(((xStep * (days)) / 3 + 200f).dp)
                    .fillMaxHeight()
                //                .background(Color.Green)
            ) {
                Log.d("Chart size", "calc= ${xStep * (days)+200f} => ${(xStep * (days)+200f).dp},  width = ${size.width}")
                val yStep = (size.height-150f) / 5

                val yAxisStart = Offset(100f,  size.height-100f)


                for(i in (minDate.time/(24*60*60*1000L)) .. maxDate.time/(24*60*60*1000L) ){
                    val date = Date(i*24*60*60*1000)
                    Log.d("Chart xaxis",date.toString())
                    val x = (date.time / (24 * 60 * 60 * 1000) - minDate.time / (24 * 60 * 60 * 1000)).toFloat() * xStep + 200f
                    if (x >= scrollState.value.toFloat()  && x <= size.width - 100f) {
                        drawContext.canvas.nativeCanvas.drawText(
                            SimpleDateFormat("MM/dd", Locale.getDefault()).format(date),
                            x,
                            yAxisStart.y + 60f,
                            textPaint
                        )
                    }
                }


                val colors: List<Color> = listOf(
                    Color(0xff5FCFEF),
                    Color(0xff00D2F3),
                )
                val shader = LinearGradientShader(Offset(0f + scrollState.value,0f), Offset(size.width + scrollState.value,0f) , colors, null, TileMode.Clamp)
                val gradientPaint = Paint().asComposePaint().asFrameworkPaint().apply {
                    setShader(shader)
                    strokeWidth = 5f
                }
                var previousX = (parsedData.firstOrNull()?.first?.time?.toFloat() ?: 0f) / (24 * 60 * 60 * 1000) * xStep + 100f
                var previousY = (yAxisStart.y - (parsedData.firstOrNull()?.second ?: 0f) * (yStep * 5) / 1000)

                parsedData.forEachIndexed { index, (date, value) ->
                    Log.d("Chart","index: $index, date: $date, value: $value")
                    val x = ((date.time  - minDate.time) * xStep / (24 * 60 * 60 * 1000)) + 200f
                    val y = yAxisStart.y - value * (yStep * 5) / 1000

                    if (index > 0) {
//                        drawLine(
//                            Color.Blue,
//                            start = Offset(previousX, previousY).coerceIn(Offset(100f, 50f), Offset(size.width + 100f, size.height - 50f)),
//                            end = Offset(x, y).coerceIn(Offset(100f, 50f), Offset(size.width + 100f, size.height - 50f)),
//                            strokeWidth = 5f
//                        )
                        drawContext.canvas.nativeCanvas.drawLine(
                            previousX,
                            previousY,
                            x,
                            y,
                            gradientPaint
                        )
                    }
                    previousX = x
                    previousY = y
                }
            }
        }
    }

}


class CreditScoreViewModel @Inject constructor() : ViewModel() {

    private var creditHistories: List<GETCreditHistoryResponse.Data.creditHistory> = emptyList()
    fun getCreditHistory(groupid: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val response = HDRetrofitBuilder.HDapiService().getCreditHistory(groupid)
            if (response.isSuccessful){
                val getresponse = response.body()
                if(getresponse != null){
                    creditHistories = getresponse.data.creditHistories
                }
            }
        }
    }
}