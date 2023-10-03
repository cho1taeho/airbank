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
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.SavingsViewModel
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import kotlin.math.min
import kotlin.math.sqrt
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.verticalScroll

@Composable
fun SavingsScreen(navController: NavController) {
    val viewModel : SavingsViewModel = hiltViewModel()
    val savingsData by viewModel.savingsState.collectAsState()

    var denominator by remember { mutableStateOf(4) }
    var numerator by remember { mutableStateOf(2) }
    var showDenominatorDropdown by remember { mutableStateOf(false) }
    var showNumeratorDropdown by remember { mutableStateOf(false) }


    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 20.dp)
            .verticalScroll(rememberScrollState())

    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .height(470.dp)
            .background(color = Color(0xFFD6F2FF))
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    "신상 운동화",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)


                )
                Spacer(modifier = Modifier.size(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.item),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(280.dp)
                        .align(Alignment.CenterHorizontally)
                )


                Spacer(modifier = Modifier.size(15.dp))
                Text(
                    "830,000원",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "기간 : 23.02.10 ~ 23.11.09",
                    fontSize = 13.sp
                )
                Text(
                    "요청금액 : 83,000원",
                    fontSize = 13.sp
                )
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xFFD6F2FF))
                .clickable{
                    navController.navigate("childSavings")
                }
        ){
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "현재 납입 금액 : 415,000",
                    fontSize =  22.sp,
                    modifier = Modifier
                        .padding(start = 14.dp)
                )
                Spacer(modifier = Modifier.size(9.dp))
                Text(
                    "밀린 횟수 : 1회",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(start = 14.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Button(
                onClick = { navController.navigate("savingsApprove") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("티끌 수락")
            }
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = { navController.navigate("savingsTransfer") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("티끌 송금")
            }
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = { navController.navigate("savingsBonus") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("티끌 보너스")
            }
        }
    }
}


