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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.myapplication.model.Resource
import com.example.myapplication.model.State
import com.example.myapplication.model.UpdateSavingsRequest
import com.example.myapplication.model.UpdateSavingsResponse

//@Preview
@Composable
fun SavingsApproveScreen(navController: NavController) {
    val viewModel : SavingsViewModel = hiltViewModel()
    val savingsData by viewModel.savingsState.collectAsState(initial = null)



    savingsData?.let { data ->
        val imageUrl = data?.data?.data?.savingsItem?.imageUrl
        val painter = rememberImagePainter(data = imageUrl)

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp, 20.dp, 20.dp)
                .verticalScroll(rememberScrollState())
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
                        "${data.data?.data?.savingsItem?.name}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)


                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(280.dp)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.size(15.dp))
                    Text(
                        "${data.data?.data?.myAmount}원",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "기간 : ${data.data?.data?.startedAt} ~ ${data.data?.data?.endedAt}",
                        fontSize = 13.sp
                    )
                    Text(
                        "요청금액 : ${data.data?.data?.savingsItem?.amount}원",
                        fontSize = 13.sp
                    )
                }
            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(70.dp)
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(color = Color(0xFFD6F2FF))
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxHeight()
//                ) {
//                    Spacer(modifier = Modifier.size(10.dp))
//                    Image(
//                        painter = painterResource(id = R.drawable.shoppingcart),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(30.dp)
//                    )
//                    Spacer(modifier = Modifier.size(10.dp))
//                    Text(
//                        "구매하기",
//                        fontSize = 19.sp,
//                        fontWeight = FontWeight.Bold
//
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//                    Image(
//                        painter = painterResource(id = R.drawable.vector),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(20.dp)
//
//                    )
//
//                }
//            }

            Spacer(modifier = Modifier.weight(1f))

            val updateSavingsState = viewModel.updateSavingsState.collectAsState(Resource(State.LOADING, null, null)).value

            if (updateSavingsState.status == State.SUCCESS) {
                navController.navigate("SavingsScreen") {
                    popUpTo("route_start_destination") { inclusive = true }
                }
            }




            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0xFFD6F2FF))
                        .clickable {
                            val groupId = 1
                            val request = UpdateSavingsRequest(isAccept = true)
                            viewModel.updateSavings(groupId, request)
                        }
                ) {
                    Text(
                        "수락하기",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0xFFD6F2FF))
                        .clickable {
                            val groupId = 1
                            val request = UpdateSavingsRequest(isAccept = false)
                            viewModel.updateSavings(groupId, request)
                        }
                ) {
                    Text(
                        "거절하기",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

