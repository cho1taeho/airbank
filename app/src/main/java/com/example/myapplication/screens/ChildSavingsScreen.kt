package com.example.myapplication.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.SavingsViewModel
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.model.CancelSavingsRequest

//@Preview
@Composable
fun ChildSavingsScreen(navController: NavController) {
    val viewModel : SavingsViewModel = hiltViewModel()
    val savingsData by viewModel.savingsData.observeAsState(initial = null)

    Log.d("ChildSavingsScreen", "savingsData: $savingsData")

    if (savingsData == null) {
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp, 20.dp, 20.dp)
        ){
            Spacer(modifier = Modifier.size(5.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .height(350.dp)
                    .background(color = Color(0xFFD6F2FF))

            ){
                Image(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                )

            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color(0xFF00D2F3))
                    .clickable {
                        navController.navigate("savingsApplication")
                    }
            ) {
                Text(
                    "신청하기",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    } else {

        savingsData?.let { data ->
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 20.dp)
            ) {
                Spacer(modifier = Modifier.size(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .height(470.dp)
                        .background(color = Color(0xFFD6F2FF))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "${data.savingsItem.name}",
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
                            contentDescription = null,
                            modifier = Modifier
                                .size(280.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.size(15.dp))
                        Text(
                            "${data.myAmount}원",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            "기간 : ${data.startedAt} ~ ${data.endedAt}",
                            fontSize = 13.sp
                        )
                        Text(
                            "요청금액 : ${data.savingsItem.amount}원",
                            fontSize = 13.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0xFFD6F2FF))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Spacer(modifier = Modifier.size(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.shoppingcart),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            "구매하기",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold

                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = R.drawable.vector),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)

                        )

                    }
                }

                Spacer(modifier = Modifier.weight(1f))


                val cancelSuccess by viewModel.cancelSuccess.observeAsState(initial = false)

                if (cancelSuccess) {

                    navController.navigate("savingsApplication") {
                        popUpTo("route_start_destination") { inclusive = true }
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0xFF00D2F3))
                        .clickable {
//                            navController.navigate("savingsApplication")
                            val groupId = 1
                            viewModel.cancelSavings(CancelSavingsRequest(id = groupId))
                        }
                ) {
                    Text(
                        "포기하기",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

