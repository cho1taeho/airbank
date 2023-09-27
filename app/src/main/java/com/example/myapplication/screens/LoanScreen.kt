package com.example.myapplication.screens


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import android.content.Intent
import androidx.compose.material3.Divider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun LoanScreen(navController: NavController) {
    Column(
    ) {
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
                        .padding(start = 13.dp)
                ) {
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        "땡겨쓰기 가능 금액",
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "2,500,000원",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        "현도 금액: ",
                        fontSize = 14.sp,
                        color = Color(0xff515151)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        "사용 금액: ",
                        fontSize = 14.sp,
                        color = Color(0xff515151)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(17.dp))
        Divider(
            color = Color(0xffCBCBCB),
            thickness = 1.dp
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.LightGray)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 13.dp)
                ) {
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        "이자",
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "150,000원",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        "이번 달 이자: ",
                        fontSize = 14.sp,
                        color = Color(0xff515151)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        "연체 이자: ",
                        fontSize = 14.sp,
                        color = Color(0xff515151)
                    )
                }
            }
            Spacer(modifier = Modifier.size(17.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.LightGray)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 13.dp)
                ) {
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        "신용점수",
                        fontSize = 16.sp,
                    )
                    ScoreBar(score = 500)
                    CreditPoint()
                }
            }
        }
    }
}