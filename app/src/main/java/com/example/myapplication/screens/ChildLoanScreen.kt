package com.example.myapplication.screens


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.AirbankApplication
import com.example.myapplication.model.GETGroupsResponse
import com.example.myapplication.viewmodel.AccountViewModel
import com.example.myapplication.viewmodel.LoanViewModel
import java.text.NumberFormat
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChildLoanScreen(navController: NavController) {
    var groupId by remember { mutableStateOf("") }
    groupId = AirbankApplication.prefs.getString("group_id", "")
    val loanViewModel: LoanViewModel = hiltViewModel();
    val loanData by loanViewModel.loanState.collectAsState()
    val accountViewModel: AccountViewModel = hiltViewModel()
    val interestData by accountViewModel.interestCheckState.collectAsState(initial = null)
    val viewModel: MainViewModel = viewModel()
    var selectChild by remember { mutableStateOf(GETGroupsResponse.Data.Member(0,0,"","",0)) }
    selectChild = viewModel.selected ?: viewModel.childs.firstOrNull() ?: GETGroupsResponse.Data.Member(0,0,"","",0)
    var creditScore = AirbankApplication.prefs.getString("creditScore","").toInt()
    var name = AirbankApplication.prefs.getString("name","")
    val creditPoint = selectChild?.creditScore ?: 0
    LaunchedEffect(key1 = groupId) {
        if (groupId.isNotEmpty()) {
            Log.d("LoanScreen", "LaunchedEffect triggered with groupId: $groupId")
            accountViewModel.interestCheck(groupId.toInt())
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
        ) {
            loanData?.let { data ->
                Box(
                    modifier = Modifier
                        .padding(16.dp)
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
                        val loanRemaining = (data.data?.data?.loanLimit ?:0) - (data.data?.data?.amount ?:0)
                        val loanLimit = data.data?.data?.loanLimit ?:0
                        val amount = data.data?.data?.amount ?:0

                        val formattedLoanRemaining = NumberFormat.getNumberInstance(Locale.US).format(loanRemaining)
                        val formattedLoamLimit = NumberFormat.getNumberInstance(Locale.US).format(loanLimit)
                        val formattedAmount = NumberFormat.getNumberInstance(Locale.US).format(amount)

                        Text(
                            "${formattedLoanRemaining}원",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            "한도 금액: ${formattedLoamLimit}원",
                            fontSize = 14.sp,
                            color = Color(0xff515151)
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            "사용 금액: ${formattedAmount}원",
                            fontSize = 14.sp,
                            color = Color(0xff515151)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(13.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(70.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color.White)
                            .clickable {
                                navController.navigate("ChildLoanRepayment")
                            }
                    ) {
                        Text(
                            "상환하기",
                            color = Color(0xFF00D2F3),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.size(10.dp))


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(70.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color(0xFF00D2F3))
                            .clickable {
                                navController.navigate("ChildLoanStart")
                            }
                    ) {
                        Text(
                            "땡겨쓰기",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
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
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(145.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.White)
                        .padding(horizontal = 16.dp)
                        .clickable {
                            navController.navigate("ChildInterestRepayment")
                        }
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

                        val interestful = (interestData?.data?.data?.amount ?:0) + (interestData?.data?.data?.overdueAmount ?:0)
                        val amount = interestData?.data?.data?.amount ?:0
                        val overdueAmount = interestData?.data?.data?.overdueAmount ?:0
                        val formattedInterestful = NumberFormat.getNumberInstance(Locale.US).format(interestful)
                        val formattedAmount = NumberFormat.getNumberInstance(Locale.US).format(amount)
                        val formattedOverdueAmount = NumberFormat.getNumberInstance(Locale.US).format(overdueAmount)

                        Text(
                            "${formattedInterestful}원",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            "이번 달 이자: ${formattedAmount}원",
                            fontSize = 14.sp,
                            color = Color(0xff515151)
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            "연체 이자: ${formattedOverdueAmount}원",
                            fontSize = 14.sp,
                            color = Color(0xff515151)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(17.dp))
                CreditScoreBox(name)

            }
        }
    }
}