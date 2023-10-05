package com.example.myapplication.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.AirbankApplication
import com.example.myapplication.R
import com.example.myapplication.model.GETGroupsResponse
import com.example.myapplication.network.HDRetrofitBuilder
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@Composable
fun ChildMainScreen(navController: NavController) {
    val viewModel : ChildMainViewModel = viewModel()
    var name = AirbankApplication.prefs.getString("name","")
    var imagepath = AirbankApplication.prefs.getString("imageUrl","")
    var creditScore = AirbankApplication.prefs.getString("creditScore","").toInt()

    var group_id by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit){
        viewModel.getGroup()
        val mutablegroupid = viewModel.groupid
        group_id = mutablegroupid
    }

    Log.d("현도id","${group_id}희찬아")
    UserApiClient.instance.me { user, _ ->
        if (user!=null){
            imagepath = user.properties?.get("profile_image") ?: ""
        }
    }
    if (imagepath.isNullOrEmpty()) {
        imagepath = "local"
    }
    Column {
        ChildCard(name,imagepath,creditScore)
        ChildBody(navController = navController)
        Spacer(modifier = Modifier.size(16.dp))
        Quiz()
    }
}

@Composable
fun ChildCard(name: String, img: String, creditScore: Int) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF5FCFEF))
    ){
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(img.isEmpty()){
                    Image(
                        painter = painterResource(R.drawable.karina),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }else{
                    AsyncImage(
                        model = img ?: "",
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(Modifier.width(16.dp))
                Text("자녀 ${name ?: ""} 님의\n지갑을 관리하고 있습니다.")
            }
            Text("신용점수")
            val creditPoint = creditScore ?: 0
            ScoreBar(creditPoint)
        }
    }
    Spacer(modifier = Modifier.size(20.dp))
}

@Composable
fun ChildBody(navController: NavController) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(300.dp)
    ){
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xFFFFE9E8))
                .clickable {
                    navController.navigate("childWallet")
                }
        ){
            Column (
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)

            ){
                Text(
                    "용돈\n사용 현황",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "이번 달에는\n얼마를 썼을까?",
                    fontSize = 13.sp,
                    lineHeight = 17.sp
                )

            }
            Image(
                painter = painterResource(id = R.drawable.wallet),
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.BottomEnd)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFE2ECFF))
                    .clickable {
                        navController.navigate("ChildLoan")
                    }
            ){
                Column (
                    modifier = Modifier
                        .padding(top=10.dp, start = 10.dp)
                ){
                    Text(
                        "땡겨쓰기\n현황",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "빌려간 돈\n확인하기",
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                    )

                }

                Image(
                    painter = painterResource(id = R.drawable.loan),
                    contentDescription = null,
                    modifier = Modifier
                        .size(130.dp)
                        .offset(x = 18.dp)
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFEFE4FF))
                    .clickable {
                        navController.navigate("childSavings")
                    }
            ){
                Column (
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                ){
                    Text(
                        "티끌 모으기\n현황",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "저금 현황\n확인하기",
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.savings),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }
    }
}





@Composable
fun Quiz(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(80.dp)
            .background(color = Color.Gray)

    ){
        Text(
            "오늘의 퀴즈",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

class ChildMainViewModel @Inject constructor() : ViewModel() {

    var groupid by mutableIntStateOf(0)
    var childs : List<GETGroupsResponse.Data.Member> = emptyList()
    var child: GETGroupsResponse.Data.Member? by mutableStateOf(null)

//    fun getGroup() {
//        val tag = "getGroup"
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = HDRetrofitBuilder.HDapiService().getGroups()
//                if (response.body() != null) {
//                    val getGroupsResponse = response.body()!!.data
//                    groupid = getGroupsResponse.members.first().groupId
//                    AirbankApplication.prefs.setString("group_id",groupid.toString())
//                    Log.d(tag,groupid.toString())
//                } else { Log.e("MainViewModel", "Response not successful: ${response.code()}") }
//            } catch (e: Exception) { Log.e("MainViewModel", "Error: ${e.message}")  }
//        }
//    }
    fun getGroup() {
        val tag = "getGroup"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = HDRetrofitBuilder.HDapiService().getGroups()
                if (response.body() != null) {
                    val getGroupsResponse = response.body()!!.data
                    childs = getGroupsResponse.members
                    if (childs.isNullOrEmpty()){Log.e("ChildMainViewModel","Child empty")}
                    else{
                        AirbankApplication.prefs.setString("group_id",childs.first().groupId.toString())
                        child = childs.first()
                        Log.d(tag,childs.first().groupId.toString())
                    }
                } else { Log.e("ChildMainViewModel", "Response not successful: ${response.code()}") }
            } catch (e: Exception) { Log.e("ChildMainViewModel", "Error: ${e.message}")  }
        }
    }
}