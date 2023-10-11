package com.airbank.myapplication.screens

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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbank.myapplication.R
import com.airbank.myapplication.model.GETGroupsResponse
import com.airbank.myapplication.model.State
import com.airbank.myapplication.network.HDRetrofitBuilder
import com.airbank.myapplication.viewmodel.AccountViewModel
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@Composable
fun ChildMainScreen(navController: NavController) {
    val viewModel : ChildMainViewModel = viewModel()
    var name = com.airbank.myapplication.AirbankApplication.prefs.getString("name","")
    var imagepath = com.airbank.myapplication.AirbankApplication.prefs.getString("imageUrl","")
    var creditScore = com.airbank.myapplication.AirbankApplication.prefs.getString("creditScore","").toInt()

    var group_id by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit){
        viewModel.getGroup(navController)
        viewModel.getMembers()
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
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))
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
            .background(Color(0xFFD6F2FF))
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
                Text("자녀 ${name ?: ""} 님!\n반가워요!",
                    fontSize = 20.sp)
            }
            val creditPoint = creditScore ?: 0
            ScoreBar(creditPoint)
        }
    }
    Spacer(modifier = Modifier.size(20.dp))
}

@Composable
fun ChildBody(navController: NavController) {
    val accountViewModel: AccountViewModel = hiltViewModel()
    val accountData by accountViewModel.accountCheckState.collectAsState(initial = null)
    val confiscationCheckState by accountViewModel.confiscationCheckState.collectAsState(initial = null)

    var groupId by remember { mutableStateOf("") }
    groupId = com.airbank.myapplication.AirbankApplication.prefs.getString("group_id", "")
    LaunchedEffect(key1 = groupId) {
        if (groupId.isNotEmpty()){
            accountViewModel.accountCheck()
            accountViewModel.confiscationCheck(groupId.toInt())
            accountViewModel.taxCheck(groupId.toInt())
        }
    }

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
                    navController.navigate("ChildWallet")
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
                    .let { modifier ->
                        when (confiscationCheckState?.status) {
                            State.SUCCESS -> {
                                if (confiscationCheckState?.data?.data?.startedAt != null) {
                                    modifier.clickable {
                                        navController.navigate("ChildConfiscationTransfer")
                                    }
                                } else {
                                    modifier.clickable {
                                        navController.navigate("ChildLoan")
                                    }
                                }
                            }
                            else -> modifier
                        }
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
                when (confiscationCheckState?.status) {
                    State.SUCCESS -> {
                        if (confiscationCheckState?.data?.data?.startedAt != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Gray.copy(alpha = 0.5f))
                            ) {
                                Text(
                                    "압류중",
                                    color = Color.White,
                                    fontSize = 23.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                    State.ERROR -> {
                    }
                    else -> Unit
                }
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
    var tempid by mutableIntStateOf(0)
    var childs : List<GETGroupsResponse.Data.Member> = emptyList()
    var child: GETGroupsResponse.Data.Member? by mutableStateOf(null)


    fun getMembers() {
        val tag = "getMember"
        viewModelScope.launch(Dispatchers.IO) {
            val response = HDRetrofitBuilder.HDapiService().getUserInfo()
            if (response.body() != null){
                val getMembersResponse = response.body()!!.data
                com.airbank.myapplication.AirbankApplication.prefs.setString("creditScore",getMembersResponse.creditScore.toString())
                Log.d(tag,"CreditScore= ${com.airbank.myapplication.AirbankApplication.prefs.getString("creditScore","")}")
            }
        }
    }

    fun getGroup(navController: NavController) {
        val tag = "getGroup"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = HDRetrofitBuilder.HDapiService().getGroups()
                if (response.body() != null) {
                    val getGroupsResponse = response.body()!!.data
                    childs = getGroupsResponse.members
                    if (childs.isNullOrEmpty()){
                        Log.e("ChildMainViewModel","Child empty, get Temporary id")
                        groupid = 0
                        getGroupEnroll(navController = navController)
                    }
                    else{
                        com.airbank.myapplication.AirbankApplication.prefs.setString("group_id",childs.first().groupId.toString())
                        child = childs.first()
                        Log.d(tag,childs.first().groupId.toString())
                    }
                } else { Log.e("ChildMainViewModel", "Response not successful: ${response.code()}") }
            } catch (e: Exception) { Log.e("ChildMainViewModel", "Error: ${e.message}")  }
        }
    }

    fun getGroupEnroll(navController: NavController) {
        val tag = "GroupEnroll"
        if(groupid!=0){return}
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = HDRetrofitBuilder.HDapiService().getGroupsEnroll()
                if (response.body() != null) {
                    val getGroupsEnrollResponse = response.body()
                    val id = getGroupsEnrollResponse?.data?.id ?: 0
                    Log.d(tag, "id= $id")

                    tempid = id
                    com.airbank.myapplication.AirbankApplication.prefs.setString("tempid",tempid.toString())
                    withContext(Dispatchers.Main){
                        navController.navigate("GroupConfirm")
                    }

                } else {
                    Log.e(tag, "Response not successful: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error: ${e.message}")
            }
        }
    }
}