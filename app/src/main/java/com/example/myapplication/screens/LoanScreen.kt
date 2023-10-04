package com.example.myapplication.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Divider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.AirbankApplication
import com.example.myapplication.viewmodel.LoanViewModel


@Composable
fun LoanScreen(navController: NavController) {
    var groupId by remember { mutableStateOf("") }
    groupId = AirbankApplication.prefs.getString("group_id", "")
    val loanViewModel: LoanViewModel = hiltViewModel();
    val loanData by loanViewModel.loanState.collectAsState()


    Column(
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            loanData?.let { data ->
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
                            "현도 금액: ${data.data?.data?.loanLimit}",
                            fontSize = 14.sp,
                            color = Color(0xff515151)
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            "사용 금액: ${data.data?.data?.amount}",
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
            Spacer(modifier = Modifier.size(17.dp))
            Column(
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
                Column() {
                    Button(
                        onClick = { navController.navigate("ChildLoanStart") },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text("땡겨쓰기")
                    }
                }
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
                        val creditPoint = AirbankApplication.prefs.getString("creditScore", "")
                        Text(
                            "신용점수 ${creditPoint}p",
                            fontSize = 16.sp,
                        )
                        ScoreBar(score = 500)
                        CreditPoint()
                    }
                }

            }
        }
    }
}